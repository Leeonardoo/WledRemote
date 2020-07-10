package br.com.leonardo.wledremote.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.adapter.InfoAdapter
import br.com.leonardo.wledremote.databinding.FragmentInfoBinding
import br.com.leonardo.wledremote.model.ui.InfoItem
import br.com.leonardo.wledremote.ui.fragment.viewmodel.DashboardViewModel


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val testInfos = listOf(
            InfoItem("Power Usage", R.drawable.power_outline_primary_24dp, "2645mA"),
            InfoItem("Uptime", R.drawable.timeline_primary_24dp, "2:13:22 since last reset")
        )

        val adapter = InfoAdapter(testInfos)
        binding.infoItemsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.infoItemsRecyclerView.adapter = adapter

        val spacing = resources.getDimensionPixelSize(R.dimen.info_item_spacing) / 2

        binding.infoItemsRecyclerView.setPadding(spacing, spacing, spacing, spacing)
        binding.infoItemsRecyclerView.clipToPadding = false
        binding.infoItemsRecyclerView.clipChildren = false
        binding.infoItemsRecyclerView.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.set(spacing, spacing, spacing, spacing)
//                super.getItemOffsets(outRect, view, parent, state)
            }
        })
    }
}