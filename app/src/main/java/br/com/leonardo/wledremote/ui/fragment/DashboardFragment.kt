package br.com.leonardo.wledremote.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.databinding.FragmentDashboardBinding
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import br.com.leonardo.wledremote.ui.fragment.viewmodel.DashboardViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import com.google.android.material.slider.Slider
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var sharedPrefs: SharedPrefsUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashViewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        sharedPrefs = SharedPrefsUtil.getInstance(requireContext())

        binding.colorToggleGroup.clearChecked()
        binding.colorToggleGroup.check(viewModel.checkedButton.value ?: R.id.button1)

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.dashboardSwipeLayout.setOnRefreshListener { mainViewModel.refreshAll() }

        binding.dashboardRetry.setOnClickListener { mainViewModel.refreshAll() }

        binding.colorToggleGroup.addOnButtonCheckedListener { _, checkedId, _ ->
            viewModel.checkedButton.value = checkedId
        }

        binding.colorPickerContainer.setOnClickListener {
            ColorPickerDialog.Builder(requireContext(), R.style.RoundedColorDialog).apply {
                setTitle(getString(R.string.select_color))
                setPositiveButton(getString(R.string.ok),
                    ColorEnvelopeListener { envelope, fromUser ->
                        if (fromUser) mainViewModel.setColor(envelope.color, viewModel.checkedButton.value ?: R.id.button1)
                    })
                setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                attachAlphaSlideBar(false)
                attachBrightnessSlideBar(false)
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
    }

    private fun setObservers() {
        mainViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.dashboardSwipeLayout.isRefreshing = it
        })

        mainViewModel.palettes.observe(viewLifecycleOwner, Observer {
            val adapter = ArrayAdapter(
                requireContext(), R.layout.support_simple_spinner_dropdown_item, it
            )
            binding.paletteDropdownMenu.setAdapter(adapter)
            binding.paletteDropdownMenu.setOnItemClickListener { _, _, position, _ ->
                mainViewModel.setPalette(position)
            }

            if (mainViewModel.state.value != null) {
                binding.paletteDropdownMenu.setText(
                    mainViewModel.state.value?.segments?.first()?.paletteId?.let { it1 -> it[it1] },
                    false
                )
            }
        })

        mainViewModel.state.observe(viewLifecycleOwner, Observer {
            viewModel.setError(false)
            //Update ui

            if (mainViewModel.palettes.value != null) {
                binding.paletteDropdownMenu.setText(
                    mainViewModel.state.value?.segments?.first()?.paletteId?.let { it1 ->
                        mainViewModel.palettes.value?.get(it1)
                    }, false
                )
            }
            it.brightness?.let { it1 -> binding.brightnessSlider.value = it1.toFloat() }
        })

        mainViewModel.isError.observe(viewLifecycleOwner, Observer {
            viewModel.setError(it, mainViewModel.stateError.value ?: "")
        })
    }
}
