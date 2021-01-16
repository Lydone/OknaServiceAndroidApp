package com.lydone.okna_service_android_app.presentation.calculator.converter

import androidx.annotation.IdRes
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowSashesCount

object ChipIdToWindowSashesCountConverter {

    fun convert(@IdRes chipId: Int) = when (chipId) {
        R.id.sashes_1 -> WindowSashesCount.ONE
        R.id.sashes_2 -> WindowSashesCount.TWO
        R.id.sashes_3 -> WindowSashesCount.THREE
        else -> throw IllegalArgumentException("Incorrect chip id: $chipId")
    }

    @IdRes
    fun convertBack(windowSashesCount: WindowSashesCount) = when (windowSashesCount) {
        WindowSashesCount.ONE -> R.id.sashes_1
        WindowSashesCount.TWO -> R.id.sashes_2
        WindowSashesCount.THREE -> R.id.sashes_3
    }
}