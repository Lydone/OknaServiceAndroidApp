package com.lydone.okna_service_android_app.data.remote.converter

import androidx.room.TypeConverter
import com.lydone.okna_service_android_app.domain.model.GlassUnitType

object GlassUnitTypeConverter {

    @TypeConverter
    fun fromType(glassUnitType: GlassUnitType) = when (glassUnitType) {
        GlassUnitType.SINGLE_CHAMBERED -> 1
        GlassUnitType.DOUBLE_CHAMBERED -> 2
    }

    @TypeConverter
    fun toType(value: Int) = when (value) {
        1 -> GlassUnitType.SINGLE_CHAMBERED
        2 -> GlassUnitType.DOUBLE_CHAMBERED
        else -> throw IllegalArgumentException("Incorrect value: $value")
    }
}