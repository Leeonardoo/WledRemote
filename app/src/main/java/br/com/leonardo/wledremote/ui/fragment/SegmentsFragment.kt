package br.com.leonardo.wledremote.ui.fragment;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.com.leonardo.wledremote.databinding.FragmentSegmentsBinding
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi;

@ExperimentalCoroutinesApi
class SegmentsFragment: Fragment() {

    private lateinit var binding: FragmentSegmentsBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSegmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setObservers()
        setListeners()
    }

    private fun setObservers() {

    }

    private fun setListeners() {

    }
}
