package com.lydone.okna_service_android_app.presentation.common

import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.WindowType

object WindowTypeToDrawableResConverter {

    fun convert(type: WindowType) = when (type) {
        WindowType.ONE_SASH -> R.drawable.window_1_sash
        WindowType.TWO_SASHES -> R.drawable.window_2_sashes
        WindowType.THREE_SASHES -> R.drawable.window_3_sashes
    }
}