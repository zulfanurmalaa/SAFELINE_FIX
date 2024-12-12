package com.capstone.fall_guard.ui.main.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.capstone.fall_guard.R
import com.capstone.fall_guard.data.local.AccountPreferences
import com.capstone.fall_guard.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.observeOn
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModel()

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            (result.data?.data as Uri).let { uri ->
                profileViewModel.saveImageProfile(uri.toString())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        overrideBackPress()

        initViews()
        setListeners()

        return root
    }

    private fun initViews() {
        profileViewModel.getImageProfile().asLiveData().observe(viewLifecycleOwner) {
            if (it != AccountPreferences.preferencesDefaultValue) {
                binding.ivProfile.setImageURI(Uri.parse(it))
            }
        }

        binding.apply {
            edName.setText(if (profileViewModel.usernameProfile != AccountPreferences.preferencesDefaultValue) profileViewModel.usernameProfile else "")
            edTelp.setText(if (profileViewModel.telpProfile != AccountPreferences.preferencesDefaultValue) profileViewModel.telpProfile else "")
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            btnPic.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "")
                galleryLauncher.launch(chooser)
            }

            edName.addTextChangedListener {
                profileViewModel.saveUsername(it.toString())
            }

            edTelp.addTextChangedListener {
                profileViewModel.saveTelp(it.toString())
            }

            btnMetrics.setOnClickListener {
                val action = ProfileFragmentDirections.actionNavigationProfileToNavigationMetrics()
                findNavController().navigate(action)
            }

            btnHome.setOnClickListener {
                val action = ProfileFragmentDirections.actionNavigationProfileToNavigationMetrics()
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