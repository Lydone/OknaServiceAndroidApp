package com.lydone.okna_service_android_app.presentation.login.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentPhoneNumberBinding
import com.lydone.okna_service_android_app.presentation.core.AfterTextChangedWatcher
import com.lydone.okna_service_android_app.presentation.core.hideKeyboard
import com.lydone.okna_service_android_app.presentation.core.setTextIgnoringTextWatcher
import com.lydone.okna_service_android_app.presentation.core.showKeyboard
import com.lydone.okna_service_android_app.presentation.login.model.LoginViewModel

class PhoneNumberFragment : Fragment(R.layout.fragment_phone_number) {

    private val viewModel by hiltNavGraphViewModels<LoginViewModel>(R.id.graph_login)

    private val phoneNumberTextWatcher = object : AfterTextChangedWatcher {
        override fun afterTextChanged(s: Editable?) {
            viewModel.phoneNumber = s.toString()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentPhoneNumberBinding.bind(view)) {
            setupLinearProgressIndicator(linearProgressIndicator)
            setupPhoneNumberTextInputEditText(phoneNumberTextInputEditText)
            setupNextButton(nextButton, phoneNumberTextInputEditText)
            setupSmsCodeNavigation()
        }
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        indicator.showAnimationBehavior = LinearProgressIndicator.SHOW_INWARD
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { isShown ->
            if (isShown) indicator.show() else indicator.hide()
        }
    }

    private fun setupPhoneNumberTextInputEditText(editText: TextInputEditText) {
        activity?.let { editText.showKeyboard(it) }
        editText.addTextChangedListener(phoneNumberTextWatcher)
        viewModel.phoneNumberLiveData.observe(viewLifecycleOwner) { text ->
            editText.setTextIgnoringTextWatcher(text, phoneNumberTextWatcher)
        }
    }

    private fun setupNextButton(button: Button, editText: TextInputEditText) {
        button.setOnClickListener {
            activity?.let { editText.hideKeyboard(it) }
            viewModel.onSendSmsCodeButtonClicked()
        }
    }

    private fun setupSmsCodeNavigation() {
        viewModel.navigateToSmsCodeLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(PhoneNumberFragmentDirections.actionPhoneNumberFragmentToSmsCodeFragment())
        }
    }

}