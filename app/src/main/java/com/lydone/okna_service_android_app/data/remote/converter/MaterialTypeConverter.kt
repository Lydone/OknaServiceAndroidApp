package com.lydone.okna_service_android_app.data.remote.converter

import androidx.room.TypeConverter
import com.lydone.okna_service_android_app.domain.model.MaterialType

object MaterialTypeConverter {

    @TypeConverter
    fun fromType(materialType: MaterialType?) = when (materialType) {
        MaterialType.BUDGET -> 1
        MaterialType.OPTIMUM -> 2
        MaterialType.PREMIUM -> 3
        else -> throw IllegalArgumentException("Can't be null")
    }

    @TypeConverter
    fun toType(value: Int) = when (value) {
        1 -> MaterialType.BUDGET
        2 -> MaterialType.OPTIMUM
        3 -> MaterialType.PREMIUM
        else -> throw IllegalArgumentException("Incorrect value: $value")
    }
}