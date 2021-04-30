package com.lydone.okna_service_android_app.presentation.profile.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentProfileBinding
import com.lydone.okna_service_android_app.presentation.core.RequestKeys
import com.lydone.okna_service_android_app.presentation.profile.model.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentProfileBinding.bind(view)) {
            setupNameTextView(nameTextView)
            setupEmailTextView(emailTextView)
            setupPhoneNumberTextView(phoneNumberTextView)
        }
        setupNavigation()
        setLoggedInFragmentResultListener()
    }

    private fun setupNameTextView(textView: TextView) {
        viewModel.userInfoLiveData.observe(viewLifecycleOwner) { textView.text = it.name }
    }

    private fun setupEmailTextView(textView: TextView) {
        viewModel.userInfoLiveData.observe(viewLifecycleOwner) { textView.text = it.email }
    }

    private fun setupPhoneNumberTextView(textView: TextView) {
        viewModel.userInfoLiveData.observe(viewLifecycleOwner) { textView.text = it.phoneNumber }
    }

    private fun setupNavigation() {
        viewModel.navDirectionsLiveData.observe(viewLifecycleOwner) { findNavController().navigate(it) }
    }

    private fun setLoggedInFragmentResultListener() {
        setFragmentResultListener(RequestKeys.KEY_LOGGED_IN) { _, _ -> viewModel.loadUserInfo() }
    }
}