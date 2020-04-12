package br.com.leonardo.wledremote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.databinding.FragmentDashboardBinding
import br.com.leonardo.wledremote.ui.viewmodel.DashboardFragmentViewModel
import com.airbnb.lottie.LottieCompositionFactory


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

        binding.dashboardFragmentPicker.subscribe { color, fromUser, shouldPropagate ->
            if (fromUser) {
                binding.dashViewmodel!!.setColor(color)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
