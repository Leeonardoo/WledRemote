package br.com.leonardo.wledremote.ui.fragment;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.adapter.SegmentsAdapter
import br.com.leonardo.wledremote.databinding.FragmentSegmentsBinding
import br.com.leonardo.wledremote.model.state.State
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SegmentsFragment : Fragment() {

    private lateinit var binding: FragmentSegmentsBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: SegmentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSegmentsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SegmentsAdapter()

        binding.segmentItemsRecyclerView.adapter = adapter
        binding.segmentItemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.segmentItemsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayout.VERTICAL
            )
        )

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        mainViewModel.state.observe(viewLifecycleOwner) { displaySegments(it) }
    }

    private fun setListeners() {
        binding.addSegmentButton.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.row_segment_item, null, false)

            AlertDialog.Builder(requireContext())
                .setTitle("Add New Segment")
                .setView(view)
                .setPositiveButton("Add") { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun displaySegments(state: State) {
        val list = state.segments?.filterNotNull() ?: listOf()
        adapter.updateList(list)
    }
}
