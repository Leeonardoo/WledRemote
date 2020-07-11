package br.com.leonardo.wledremote.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.adapter.InfoAdapter
import br.com.leonardo.wledremote.databinding.FragmentInfoBinding
import br.com.leonardo.wledremote.model.info.Info
import br.com.leonardo.wledremote.model.ui.InfoItem
import br.com.leonardo.wledremote.rest.api.ResultWrapper
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import br.com.leonardo.wledremote.ui.fragment.viewmodel.DashboardViewModel
import br.com.leonardo.wledremote.util.convertMillisToDisplay

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private val viewModel: DashboardViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: InfoAdapter

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
        adapter = InfoAdapter()

        binding.infoItemsRecyclerView.adapter = adapter
        binding.infoItemsRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

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

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.infoSwipeLayout.setOnRefreshListener { mainViewModel.getInfo() }
    }

    private fun setObservers() {
        mainViewModel.info.observe(viewLifecycleOwner, Observer { displayInfo(it) })

        mainViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.infoSwipeLayout.isRefreshing = it
        })
    }

    private fun displayInfo(info: Info) {
        val infos = listOf(
            InfoItem(
                "Power Usage",
                R.drawable.power_outline_primary_24dp,
                "${info.leds?.currentPowerUsage}mA"
            ),
            InfoItem("Uptime", R.drawable.timeline_primary_24dp, "${info.uptime?.let {
                convertMillisToDisplay(it)
            }} since last reset")
        )

        adapter.updateList(infos)
    }
}