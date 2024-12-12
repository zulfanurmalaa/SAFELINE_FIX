package com.capstone.fall_guard.ui.main.starter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.capstone.fall_guard.R
import com.capstone.fall_guard.databinding.FragmentStarterBinding

class StarterFragment : Fragment() {
    private var _binding: FragmentStarterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStarterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setListeners()

        return root
    }

    private fun setListeners() {
        binding.apply {
            btnHome.setOnClickListener {
                val action = StarterFragmentDirections.actionNavigationStarterToNavigationHome()
                findNavController().navigate(action)
            }

            btnMetrics.setOnClickListener {
                val action = StarterFragmentDirections.actionNavigationStarterToNavigationMetrics()
                findNavController().navigate(action)
            }

            btnProfile.setOnClickListener {
                val action = StarterFragmentDirections.actionNavigationStarterToNavigationProfile()
                findNavController().navigate(action)
            }
        }
    }
}