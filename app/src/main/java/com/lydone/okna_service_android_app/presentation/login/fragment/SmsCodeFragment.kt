package com.lydone.okna_service_android_app.presentation.login.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentSmsCodeBinding
import com.lydone.okna_service_android_app.presentation.core.AfterTextChangedWatcher
import com.lydone.okna_service_android_app.presentation.core.RequestKeys
import com.lydone.okna_service_android_app.presentation.core.setTextIgnoringTextWatcher
import com.lydone.okna_service_android_app.presentation.login.model.SmsCodeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmsCodeFragment : Fragment(R.layout.fragment_sms_code) {

    private val viewModel by viewModels<SmsCodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.phoneNumber = SmsCodeFragmentArgs.fromBundle(requireArguments()).phoneNumber

        with(FragmentSmsCodeBinding.bind(view)) {
            setupConfirmButton(confirmButton, smsCodeTextInputEditText)
            setupLinearProgressIndicator(linearProgressIndicator)
            setupSmsCodeTextInput(smsCodeTextInputLayout, smsCodeTextInputEditText)
            setupUserLoggedInFragmentResult()
            setupNavigation()
        }

    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        indicator.showAnimationBehavior = LinearProgressIndicator.SHOW_INWARD
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { isProgressShown ->
            if (isProgressShown) indicator.show() else indicator.hide()
        }
    }

    private fun setupSmsCodeTextInput(layout: TextInputLayout, editText: TextInputEditText) {
        val smsCodeTextWatcher = object : AfterTextChangedWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.smsCode = s.toString()
            }
        }
        editText.addTextChangedListener(smsCodeTextWatcher)
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { isProgressShown ->
            layout.isEnabled = !isProgressShown
        }
        viewModel.smsCodeLiveData.observe(viewLifecycleOwner) { text ->
            editText.setTextIgnoringTextWatcher(text, smsCodeTextWatcher)
        }
        viewModel.errorTextLiveData.observe(viewLifecycleOwner) { layout.error = it }
    }

    private fun setupConfirmButton(button: Button, editText: EditText) {
        button.setOnClickListener { viewModel.onConfirmButtonClicked(editText.text.toString()) }
        viewModel.isConfirmButtonEnabledLiveData.observe(viewLifecycleOwner) { button.isEnabled = it }
    }


    private fun setupUserLoggedInFragmentResult() {
        viewModel.setUserLoggedInFragmentResultLiveData.observe(viewLifecycleOwner) {
            setFragmentResult(RequestKeys.KEY_LOGGED_IN, Bundle.EMPTY)
        }
    }

    private fun setupNavigation() {
        viewModel.navDirectionsLiveData.observe(viewLifecycleOwner) { findNavController().navigate(it) }
    }
}