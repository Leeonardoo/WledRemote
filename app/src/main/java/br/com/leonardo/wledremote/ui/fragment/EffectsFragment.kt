package br.com.leonardo.wledremote.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.leonardo.wledremote.adapter.EffectsAdapter
import br.com.leonardo.wledremote.databinding.FragmentEffectsBinding
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import br.com.leonardo.wledremote.ui.fragment.viewmodel.EffectsViewModel
import br.com.leonardo.wledremote.util.getFirstSelectedSegment
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EffectsFragment : Fragment() {

    private lateinit var binding: FragmentEffectsBinding
    private val viewModel: EffectsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEffectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        mainViewModel.effects.observe(viewLifecycleOwner) {
            binding.effectsRecyclerView.adapter = EffectsAdapter(it) { effect ->
                mainViewModel.setEffect(it.indexOf(effect))
            }
            binding.effectsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        mainViewModel.state.observe(viewLifecycleOwner) {
            val segment = getFirstSelectedSegment(it)
            if (segment != null) {
                binding.effectIntensitySlider.value = (segment.effectIntensity ?: 127).toFloat()
                binding.effectSpeedSlider.value = (segment.relativeSpeed ?: 127).toFloat()

                // TODO: Add icon to selected effect
            }
        }
        mainViewModel.isLoading.observe(viewLifecycleOwner) { viewModel.setLoading(it) }
    }

    private fun setListeners() {
        binding.effectIntensitySlider.addOnSliderTouchListener(object :
            Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {
                mainViewModel.setEffectAttr(intensity = binding.effectIntensitySlider.value.toInt())
            }
        })

        binding.effectSpeedSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {
                mainViewModel.setEffectAttr(speed = binding.effectSpeedSlider.value.toInt())
            }
        })
    }
}