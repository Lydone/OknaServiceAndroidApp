package com.lydone.okna_service_android_app.presentation.core

import android.text.TextWatcher

interface AfterTextChangedWatcher : TextWatcher {

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}