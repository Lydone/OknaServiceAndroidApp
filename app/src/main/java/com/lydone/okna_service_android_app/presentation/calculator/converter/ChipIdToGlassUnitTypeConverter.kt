package com.lydone.okna_service_android_app.presentation.calculator.converter

import androidx.annotation.IdRes
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.GlassUnitType

object ChipIdToGlassUnitTypeConverter {

    fun convert(@IdRes chipId: Int) = when (chipId) {
        R.id.glass_unit_1 -> GlassUnitType.SINGLE_CHAMBERED
        R.id.glass_unit_2 -> GlassUnitType.DOUBLE_CHAMBERED
        else -> throw IllegalArgumentException("Incorrect chipId: $chipId")
    }

    @IdRes
    fun convertBack(glassUnitType: GlassUnitType) = when (glassUnitType) {
        GlassUnitType.SINGLE_CHAMBERED -> R.id.glass_unit_1
        GlassUnitType.DOUBLE_CHAMBERED -> R.id.glass_unit_2
    }
}