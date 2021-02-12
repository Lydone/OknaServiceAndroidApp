package com.lydone.okna_service_android_app.presentation.core

import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.setTextIgnoringTextWatcher(newText: String, watcher: TextWatcher) {
    removeTextChangedListener(watcher)
    text?.clear()
    text?.append(newText)
    addTextChangedListener(watcher)
}