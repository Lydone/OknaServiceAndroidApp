package com.lydone.okna_service_android_app.data.remote.converter

import androidx.room.TypeConverter
import com.lydone.okna_service_android_app.domain.model.SashType

object SashTypeConverter {

    @TypeConverter
    fun fromType(sashType: SashType) = when (sashType) {
        SashType.FIXED -> 1
        SashType.SWING_OUT -> 2
        SashType.SWING_OUT_AND_FLAP -> 3
    }

    @TypeConverter
    fun toType(value: Int) = when (value) {
        1 -> SashType.FIXED
        2 -> SashType.SWING_OUT
        3 -> SashType.SWING_OUT_AND_FLAP
        else -> throw IllegalArgumentException("Incorrect value: $value")
    }
}