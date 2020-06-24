package br.com.leonardo.wledremote.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.databinding.FragmentDashboardBinding
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import br.com.leonardo.wledremote.ui.fragment.viewmodel.DashboardViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import com.airbnb.lottie.LottieCompositionFactory
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

        setListeners()
        setObservers()

        binding.statusAnimationView.addAnimatorUpdateListener {
            if (binding.statusAnimationView.frame == 159) {
                LottieCompositionFactory.fromRawRes(context, R.raw.check).addListener {
                    binding.statusAnimationView.setComposition(it)
                    binding.statusAnimationView.playAnimation()
                    binding.statusText.text = "Connected to: ${sharedPrefs.getSavedIP()}"
                    binding.currentStatus.text = "Touch for details"
                }
            }
        }
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
        }
        )
    }

    private fun setObservers() {
        mainViewModel.palettes.observe(viewLifecycleOwner, Observer {
            //Handle response and show recyclerView
        })
    }
}
