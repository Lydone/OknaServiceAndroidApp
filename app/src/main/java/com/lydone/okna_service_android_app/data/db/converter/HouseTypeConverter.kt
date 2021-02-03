package com.lydone.okna_service_android_app.data.db.converter

import androidx.room.TypeConverter
import com.lydone.okna_service_android_app.domain.model.HouseType

class HouseTypeConverter {

    @TypeConverter
    fun fromType(houseType: HouseType) = when (houseType) {
        HouseType.PREFAB -> 1
        HouseType.BRICK -> 2
    }

    @TypeConverter
    fun toType(value: Int) = when (value) {
        1 -> HouseType.PREFAB
        2 -> HouseType.BRICK
        else -> throw IllegalArgumentException("Incorrect value: $value")
    }
}