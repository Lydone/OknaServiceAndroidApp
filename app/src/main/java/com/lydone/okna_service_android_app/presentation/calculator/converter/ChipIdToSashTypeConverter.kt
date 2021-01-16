package com.lydone.okna_service_android_app.presentation.calculator.converter

import androidx.annotation.IdRes
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.presentation.calculator.model.SashType

object ChipIdToSashTypeConverter {

    fun convert(@IdRes chipId: Int) = when (chipId) {
        R.id.sash_fixed -> SashType.FIXED
        R.id.sash_swing_out -> SashType.SWING_OUT
        R.id.sash_swing_out_and_flap -> SashType.SWING_OUT_AND_FLAP
        else -> throw IllegalArgumentException("Incorrect chipId: $chipId")
    }

    fun convertBack(sashType: SashType) = when (sashType) {
        SashType.FIXED -> R.id.sash_fixed
        SashType.SWING_OUT -> R.id.sash_swing_out
        SashType.SWING_OUT_AND_FLAP -> R.id.sash_swing_out_and_flap
    }
}