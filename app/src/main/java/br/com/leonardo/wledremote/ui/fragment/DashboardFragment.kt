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
import br.com.leonardo.wledremote.rest.api.LocalResultWrapper
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import br.com.leonardo.wledremote.ui.fragment.viewmodel.DashboardViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import com.airbnb.lottie.LottieDrawable
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
        binding.statusAnimationView.repeatCount = LottieDrawable.INFINITE
        sharedPrefs = SharedPrefsUtil.getInstance(requireContext())

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.colorPickerContainer.setOnClickListener {
            ColorPickerDialog.Builder(requireContext(), R.style.RoundedColorDialog).apply {
                setTitle(getString(R.string.select_color))
                setPositiveButton(getString(R.string.ok),
                    ColorEnvelopeListener { envelope, fromUser ->
                        if (fromUser) mainViewModel.setColor(envelope.color)
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
        mainViewModel.palettes.observe(viewLifecycleOwner, Observer {
            if (it is LocalResultWrapper.Success) {
                val adapter = ArrayAdapter<String>(
                    requireContext(), R.layout.support_simple_spinner_dropdown_item,
                    it.value
                )
                binding.paletteDropdownMenu.setAdapter(adapter)
                binding.paletteDropdownMenu.setOnItemClickListener { _, _, position, _ ->
                    mainViewModel.setPalette(position)
                }
            }
        })

        mainViewModel.currentState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LocalResultWrapper.Success -> {
                    binding.statusAnimationView.repeatCount = 0
                    binding.statusAnimationView.apply {
                        setAnimation(R.raw.done)
                        playAnimation()
                    }
                }

                //Show errors
            }
        })
    }
}
