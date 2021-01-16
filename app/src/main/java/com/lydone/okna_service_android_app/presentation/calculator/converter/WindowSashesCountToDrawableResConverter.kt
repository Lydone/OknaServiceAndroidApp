package com.lydone.okna_service_android_app.presentation.calculator.converter

import com.lydone.okna_service_android_app.R

object WindowSashesCountToDrawableResConverter {

    fun convert(sashesCount: Int) = when (sashesCount) {
        1 -> R.drawable.window_1_sash
        2 -> R.drawable.window_2_sashes
        3 -> R.drawable.window_3_sashes
        else -> throw IllegalArgumentException("Incorrect sashes count: $sashesCount")
    }
}