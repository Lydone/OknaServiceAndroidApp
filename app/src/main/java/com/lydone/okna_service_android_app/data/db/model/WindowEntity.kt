package com.lydone.okna_service_android_app.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lydone.okna_service_android_app.data.db.converter.*
import com.lydone.okna_service_android_app.domain.model.*

@Entity
data class WindowEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val width: Int,
    val height: Int,
    @field:TypeConverters(MaterialTypeConverter::class)
    val materialType: MaterialType,
    @field:TypeConverters(WindowTypeConverter::class)
    var windowType: WindowType,
    @field:TypeConverters(SashTypeListConverter::class)
    val sashes: List<SashType>,
    @field:TypeConverters(GlassUnitTypeConverter::class)
    val glassUnitType: GlassUnitType,
    @field:TypeConverters(HouseTypeConverter::class)
    val houseType: HouseType,
    val isWindowsillIncluded: Boolean,
    val isEbbIncluded: Boolean,
    val isSlopeIncluded: Boolean,
    val isLaminationIncluded: Boolean,
    val isMosquitoNetIncluded: Boolean,
)