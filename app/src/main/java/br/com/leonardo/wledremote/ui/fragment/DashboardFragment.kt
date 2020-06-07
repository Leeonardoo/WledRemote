package br.com.leonardo.wledremote.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.databinding.FragmentDashboardBinding
import br.com.leonardo.wledremote.ui.fragment.viewmodel.DashboardFragmentViewModel
import com.airbnb.lottie.LottieCompositionFactory
import com.google.android.material.slider.Slider
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener


class DashboardFragment : Fragment() {
    lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashViewmodel = viewModel
        binding.statusAnimationView.addAnimatorUpdateListener {
            if (binding.statusAnimationView.frame == 159) {
                LottieCompositionFactory.fromRawRes(
                    context,
                    R.raw.check
                ).addListener {
                    binding.statusAnimationView.setComposition(it)
                    binding.statusAnimationView.playAnimation()
                    binding.currentStatus.text = "Connected to: 192.168.1.250"
                    binding.statusText.text = "Successfully connected!"
                }
            }
        }

        binding.colorPickerContainer.setOnClickListener {
            ColorPickerDialog.Builder(
                requireContext(), R.style.Theme_MaterialComponents_Light_Dialog_Alert
            )
                .setTitle("ColorPicker Dialog")
                .setPositiveButton(getString(R.string.ok),
                    ColorEnvelopeListener { envelope, fromUser ->
                        //do something
                    })
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                .attachAlphaSlideBar(false)
                .attachBrightnessSlideBar(false)
                .show()
        }

        binding.brightnessSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {

            override fun onStartTrackingTouch(slider: Slider) {}

            override fun onStopTrackingTouch(slider: Slider) {
                viewModel.setBrightness(slider.value.toInt())
            }
        }
        )
    }
}
