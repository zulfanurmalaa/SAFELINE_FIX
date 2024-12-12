package com.capstone.fall_guard.ui.main.home

import android.graphics.fonts.SystemFonts
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.capstone.fall_guard.R
import com.capstone.fall_guard.databinding.FragmentHomeBinding
import com.capstone.fall_guard.databinding.FragmentStarterBinding
import com.capstone.fall_guard.utils.UserActivities
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        overrideBackPress()

        observeViewModel()

        initCurrentTime()
        setListeners()

        return root
    }

    private fun observeViewModel() {
        homeViewModel.userActivity.observe(viewLifecycleOwner) { status ->
            setMonitoringView(status)
        }
    }

    private fun initCurrentTime() {
        val timestamp = System.currentTimeMillis()
        val dateFormatter = SimpleDateFormat("MMM, dd yyyy", Locale.ENGLISH)
        val timeFormatter = SimpleDateFormat("h:mm a", Locale.ENGLISH)

        val date = dateFormatter.format(timestamp)
        val time = timeFormatter.format(timestamp)

        binding.apply {
            tvDate.text = date
            tvClock.text = time
        }
    }

    private fun setMonitoringView(status: String) {
        val loweredStatus = status.lowercase()

        when (loweredStatus) {
            UserActivities.BERJALAN.name.lowercase() -> {
                binding.apply {
                    ivActivityMonitor.setImageResource(R.drawable.ic_walk)
                    tvActivityMonitor.text = status
                }
            }

            UserActivities.DIAM.name.lowercase() -> {
                binding.apply {
                    ivActivityMonitor.setImageResource(R.drawable.ic_idle)
                    tvActivityMonitor.text = status
                }
            }

            UserActivities.JATUH.name.lowercase() -> {
                binding.apply {
                    ivActivityMonitor.setImageResource(R.drawable.ic_fall)
                    tvActivityMonitor.text = status
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            btnMetrics.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationMetrics()
                findNavController().navigate(action)
            }

            btnProfile.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationProfile()
                findNavController().navigate(action)
            }
        }
    }

    private fun overrideBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.navigation_starter)
                }
            })
    }
}