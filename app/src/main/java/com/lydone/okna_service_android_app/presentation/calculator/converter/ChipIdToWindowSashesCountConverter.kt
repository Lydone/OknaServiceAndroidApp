package com.lydone.okna_service_android_app.presentation.calculator.converter

import androidx.annotation.IdRes
import com.lydone.okna_service_android_app.R

object ChipIdToWindowSashesCountConverter {

    fun convert(@IdRes chipId: Int) = when (chipId) {
        R.id.sashes_1 -> 1
        R.id.sashes_2 -> 2
        R.id.sashes_3 -> 3
        else -> throw IllegalArgumentException("Incorrect chip id: $chipId")
    }

    @IdRes
    fun convertBack(count: Int) = when (count) {
        1 -> R.id.sashes_1
        2 -> R.id.sashes_2
        3 -> R.id.sashes_3
        else -> throw IllegalArgumentException("Incorrect count: $count")
    }
}