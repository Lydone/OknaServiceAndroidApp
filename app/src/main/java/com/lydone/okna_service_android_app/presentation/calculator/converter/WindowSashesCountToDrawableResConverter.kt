package com.lydone.okna_service_android_app.presentation.calculator.converter

import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowSashesCount

object WindowSashesCountToDrawableResConverter {

    fun convert(windowSashesCount: WindowSashesCount) = when (windowSashesCount) {
        WindowSashesCount.ONE -> R.drawable.window_1_sash
        WindowSashesCount.TWO -> R.drawable.window_2_sashes
        WindowSashesCount.THREE -> R.drawable.window_3_sashes
    }
}