package com.lydone.okna_service_android_app.presentation.cart.converter

import androidx.annotation.StringRes
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.GlassUnitType

object GlassUnitTypeToStringResConverter {

    @StringRes
    fun convert(type: GlassUnitType) = when (type) {
        GlassUnitType.SINGLE_CHAMBERED -> R.string.single_chamber
        GlassUnitType.DOUBLE_CHAMBERED -> R.string.double_chamber
    }
}