package br.com.leonardo.wledremote.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.leonardo.wledremote.adapter.EffectsAdapter
import br.com.leonardo.wledremote.databinding.FragmentEffectsBinding
import br.com.leonardo.wledremote.repository.EffectStatus
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import br.com.leonardo.wledremote.ui.fragment.viewmodel.EffectsViewModel

class EffectsFragment : Fragment() {

    private lateinit var binding: FragmentEffectsBinding
    private val viewModel: EffectsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var effects: List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEffectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setObservers()
    }

    private fun setObservers() {
        mainViewModel.effects.observe(viewLifecycleOwner, Observer {
            if (it is EffectStatus.Success) {
                effects = it.effects
                binding.effectsRecyclerView.adapter = EffectsAdapter(effects) { effect ->
                    mainViewModel.setEffect(effects.indexOf(effect))
                }
                binding.effectsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }
}