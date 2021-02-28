package com.lydone.okna_service_android_app.presentation.registration.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentRegistrationBinding
import com.lydone.okna_service_android_app.presentation.core.AfterTextChangedWatcher
import com.lydone.okna_service_android_app.presentation.core.RequestKeys
import com.lydone.okna_service_android_app.presentation.core.setTextIgnoringTextWatcher
import com.lydone.okna_service_android_app.presentation.core.showKeyboard
import com.lydone.okna_service_android_app.presentation.registration.model.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val viewModel by viewModels<RegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(RegistrationFragmentArgs.fromBundle(requireArguments())) {
            viewModel.phoneNumber = phoneNumber
            viewModel.smsCode = smsCode
        }

        with(FragmentRegistrationBinding.bind(view)) {
            setupLinearProgressIndicator(linearProgressIndicator)
            setupNameTextInput(nameTextInputLayout, nameTextInputEditText)
            setupEmailTextInput(emailTextInputLayout, emailTextInputEditText)
            setupSignUpButton(signUpButton)
            setupNavigation()
        }
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        indicator.showAnimationBehavior = LinearProgressIndicator.SHOW_INWARD
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { isProgressShown ->
            if (isProgressShown) indicator.show() else indicator.hide()
        }
    }

    private fun setupNameTextInput(layout: TextInputLayout, editText: TextInputEditText) {
        editText.showKeyboard(requireContext())
        val watcher = object : AfterTextChangedWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.name = s.toString()
            }
        }
        editText.addTextChangedListener(watcher)
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { layout.isEnabled = !it }
        viewModel.nameLiveData.observe(viewLifecycleOwner) { editText.setTextIgnoringTextWatcher(it, watcher) }
    }

    private fun setupEmailTextInput(layout: TextInputLayout, editText: TextInputEditText) {
        val watcher = object : AfterTextChangedWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.email = s.toString()
            }
        }
        editText.addTextChangedListener(watcher)
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { layout.isEnabled = !it }
        viewModel.emailLiveData.observe(viewLifecycleOwner) { editText.setTextIgnoringTextWatcher(it, watcher) }
    }

    private fun setupSignUpButton(button: Button) {
        button.setOnClickListener { viewModel.onSignUpButtonClicked() }
        viewModel.isSignUpButtonEnabledLiveData.observe(viewLifecycleOwner) { button.isEnabled = it }
    }

    private fun setupNavigation() {
        viewModel.navDirectionsLiveData.observe(viewLifecycleOwner) {
            setFragmentResult(RequestKeys.KEY_LOGGED_IN, Bundle.EMPTY)
            findNavController().navigate(it)
        }
    }
}