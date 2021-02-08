package com.lydone.okna_service_android_app.presentation.cart.converter

import androidx.annotation.StringRes
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.SashType

object SashTypeToStringResConverter {

    @StringRes
    fun convert(type: SashType) = when (type) {
        SashType.FIXED -> R.string.sash_fixed
        SashType.SWING_OUT -> R.string.sash_swing_out
        SashType.SWING_OUT_AND_FLAP -> R.string.sash_swing_out_and_flap
    }
}