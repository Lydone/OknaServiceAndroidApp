package com.lydone.okna_service_android_app.data.db.converter

import androidx.room.TypeConverter
import com.lydone.okna_service_android_app.domain.model.WindowType

class WindowTypeConverter {

    @TypeConverter
    fun fromType(type: WindowType) = when (type) {
        WindowType.ONE_SASH -> 1
        WindowType.TWO_SASHES -> 2
        WindowType.THREE_SASHES -> 3
    }

    @TypeConverter
    fun toType(value: Int) = when (value) {
        1 -> WindowType.ONE_SASH
        2 -> WindowType.TWO_SASHES
        3 -> WindowType.THREE_SASHES
        else -> throw IllegalArgumentException("Incorrect value: $value")
    }
}