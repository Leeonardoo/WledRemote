package br.com.leonardo.wledremote.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.databinding.FragmentDashboardBinding
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import br.com.leonardo.wledremote.ui.fragment.viewmodel.DashboardViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import br.com.leonardo.wledremote.util.getFirstSelectedSegment
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var sharedPrefs: SharedPrefsUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashViewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        sharedPrefs = SharedPrefsUtil.getInstance(requireContext())

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.dashboardSwipeLayout.setOnRefreshListener { mainViewModel.refreshAll() }

        binding.colorPickerContainer.setOnClickListener {
            val buttonId = binding.buttonToggleGroup.checkedButtonId
            val button = binding.buttonToggleGroup.findViewById<MaterialButton>(buttonId)
            val colorIndex = binding.buttonToggleGroup.indexOfChild(button)

            ColorPickerDialog.Builder(requireContext(), R.style.RoundedColorDialog).apply {
                setTitle(getString(R.string.select_color))
                setPositiveButton(getString(R.string.ok),
                    ColorEnvelopeListener { envelope, fromUser ->
                        if (fromUser) {
                            binding.currentColor.setColorFilter(envelope.color)
                            mainViewModel.setColor(envelope.color, colorIndex)
                        }
                    })
                setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                attachAlphaSlideBar(false)
                attachBrightnessSlideBar(true)
            }.show()
        }

        binding.brightnessSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {
                mainViewModel.setBrightness(slider.value.toInt())
            }
        })

        binding.statusContainer.setOnClickListener {
            val directions = DashboardFragmentDirections.actionDashboardFragmentToInfoFragment()
            findNavController().navigate(directions)
        }

        binding.buttonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked && checkedId != -1) {
                val button = group.findViewById<MaterialButton>(checkedId)
                val index = group.indexOfChild(button)
                if (index != -1 && getFirstSelectedSegment(mainViewModel.state.value) != null) {
                    val segment = getFirstSelectedSegment(mainViewModel.state.value)
                    val color = segment!!.colors?.get(index)
                    if (color != null) {
                        val colorInt = parseIntArrayToColorInt(color)
                        binding.currentColor.setColorFilter(colorInt)
                    }
                }
            }
        }
    }

    private fun setObservers() {
        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.dashboardSwipeLayout.isRefreshing = it
            if (it == true) {
                binding.statusText.text =
                    getString(R.string.dashboard_connecting_to, sharedPrefs.getSavedIP())
                binding.currentStatus.text = ""
            }
        }

        mainViewModel.palettes.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(
                requireContext(), R.layout.support_simple_spinner_dropdown_item, it
            )
            binding.paletteDropdownMenu.setAdapter(adapter)
            binding.paletteDropdownMenu.setOnItemClickListener { _, _, position, _ ->
                mainViewModel.setPalette(position)
            }
        }

        mainViewModel.state.observe(viewLifecycleOwner) {
            //Update ui status
            if (it.brightness != null) binding.brightnessSlider.value = it.brightness.toFloat()
            if (it.segments != null) {
                val segment = getFirstSelectedSegment(it)
                val palIndex = segment?.paletteId ?: 0
                val adapter = (binding.paletteDropdownMenu.adapter)
                if (adapter != null) {
                    binding.paletteDropdownMenu.setText(adapter.getItem(palIndex) as String, false)
                }
            }
            val checkId = binding.buttonToggleGroup.checkedButtonId
            if (checkId != -1) {
                binding.buttonToggleGroup.clearChecked()
                binding.buttonToggleGroup.check(checkId)
            }

            binding.statusText.text =
                getString(R.string.dashboard_connected_to, sharedPrefs.getSavedIP())
            binding.currentStatus.text = getString(R.string.dashboard_details)
        }
    }

    @ColorInt
    fun parseIntArrayToColorInt(array: List<Int?>): Int {
        val alpha = (0xff).shl(24)
        val red = ((array[0] ?: 0) and 0xff).shl(16)
        val green = ((array[1] ?: 0) and 0xff).shl(8)
        val blue = ((array[2] ?: 0) and 0xff).shl(0)
        return alpha or red or green or blue
    }
}
