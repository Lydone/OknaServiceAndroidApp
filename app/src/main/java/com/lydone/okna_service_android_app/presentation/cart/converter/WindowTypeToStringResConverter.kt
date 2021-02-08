package com.lydone.okna_service_android_app.presentation.cart.converter

import androidx.annotation.StringRes
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.WindowType

object WindowTypeToStringResConverter {

    @StringRes
    fun convert(type: WindowType) = when (type) {
        WindowType.ONE_SASH -> R.string.one_sash_window
        WindowType.TWO_SASHES -> R.string.two_sashes_window
        WindowType.THREE_SASHES -> R.string.three_sashes_window
    }
}