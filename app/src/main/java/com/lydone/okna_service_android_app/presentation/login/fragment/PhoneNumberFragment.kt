package com.lydone.okna_service_android_app.presentation.login.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentPhoneNumberBinding
import com.lydone.okna_service_android_app.presentation.core.AfterTextChangedWatcher
import com.lydone.okna_service_android_app.presentation.core.setTextIgnoringTextWatcher
import com.lydone.okna_service_android_app.presentation.login.model.PhoneNumberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneNumberFragment : Fragment(R.layout.fragment_phone_number) {

    private val viewModel by viewModels<PhoneNumberViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentPhoneNumberBinding.bind(view)) {
            setupLinearProgressIndicator(linearProgressIndicator)
            setupPhoneNumberTextInput(phoneNumberTextInputLayout, phoneNumberTextInputEditText)
            setupNextButton(nextButton)
            setupSmsCodeNavigation()
        }
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        indicator.showAnimationBehavior = LinearProgressIndicator.SHOW_INWARD
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { isShown ->
            if (isShown) indicator.show() else indicator.hide()
        }
    }

    private fun setupPhoneNumberTextInput(layout: TextInputLayout, editText: TextInputEditText) {
        val phoneNumberTextWatcher = object : AfterTextChangedWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.phoneNumber = s.toString()
            }
        }
        editText.addTextChangedListener(phoneNumberTextWatcher)
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { layout.isEnabled = !it }
        viewModel.phoneNumberLiveData.observe(viewLifecycleOwner) { text ->
            editText.setTextIgnoringTextWatcher(text, phoneNumberTextWatcher)
        }
    }

    private fun setupNextButton(button: Button) {
        button.setOnClickListener {
            viewModel.onSendSmsCodeButtonClicked()
        }
        viewModel.isNextButtonEnabledLiveData.observe(viewLifecycleOwner) { button.isEnabled = it }
    }

    private fun setupSmsCodeNavigation() {
        viewModel.navigateToSmsCodeLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(
                PhoneNumberFragmentDirections.actionPhoneNumberFragmentToSmsCodeFragment(
                    requireNotNull(viewModel.phoneNumber)
                )
            )
        }
    }

}