package com.lydone.okna_service_android_app.presentation.login.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentSmsCodeBinding
import com.lydone.okna_service_android_app.presentation.core.showKeyboard

class SmsCodeFragment: Fragment(R.layout.fragment_sms_code) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(FragmentSmsCodeBinding.bind(view)) {
            activity?.let { smsCodeTextInputEditText.showKeyboard(it) }
        }

    }
}