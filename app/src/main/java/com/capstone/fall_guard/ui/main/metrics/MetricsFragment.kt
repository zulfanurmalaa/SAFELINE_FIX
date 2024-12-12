package com.capstone.fall_guard.ui.main.metrics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.fall_guard.R
import com.capstone.fall_guard.databinding.FragmentMetricsBinding
import com.capstone.fall_guard.databinding.FragmentStarterBinding
import com.capstone.fall_guard.ui.adapters.HistoryAdapter
import com.capstone.fall_guard.ui.main.home.HomeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class MetricsFragment : Fragment() {
    private var _binding: FragmentMetricsBinding? = null
    private val binding get() = _binding!!

    private val metricsViewModel: MetricsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMetricsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        overrideBackPress()

        observeViewModel()

        setListeners()

        return root
    }

    private fun observeViewModel() {
        metricsViewModel.fallHistoryList.observe(viewLifecycleOwner) { fallHistoryList: MutableList<Long> ->
            binding.historyLayout.removeAllViews()


            // Format and group timestamps by day
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val groupedByDay = fallHistoryList.groupBy { timestamp ->
                dateFormatter.format(Date(timestamp)) // Format timestamp to day string
            }

            // Process each day group
            groupedByDay.forEach { (day, timestamps) ->
                // Convert the day string back to a timestamp for injecting the section
                val sectionTimestamp = timestamps.first()
                injectDaySectionHistory(sectionTimestamp, timestamps.toMutableList())
            }
        }
    }

    private fun injectDaySectionHistory(timestamp: Long, fallHistory: MutableList<Long>) {
        val dateFormatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        val timeFormatter = SimpleDateFormat("h:mm a", Locale.ENGLISH)

        val sectionText = dateFormatter.format(timestamp)
        val mappedFallListString =
            fallHistory.map { timeFormatter.format(it) ?: "" }.toMutableList()

        binding.apply {
            val tvSection = TextView(requireContext()).apply {
                text = sectionText
                textSize = 18f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                setPadding(16, 16, 16, 8)
            }

            val rvHistory = RecyclerView(requireContext()).apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = HistoryAdapter().apply {
                    submitList(mappedFallListString)
                }
                setPadding(16, 0, 16, 16)
            }

            historyLayout.addView(tvSection)
            historyLayout.addView(rvHistory)
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            btnHome.setOnClickListener {
                val action = MetricsFragmentDirections.actionNavigationMetricsToNavigationHome()
                findNavController().navigate(action)
            }

            btnProfile.setOnClickListener {
                val action = MetricsFragmentDirections.actionNavigationMetricsToNavigationProfile()
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