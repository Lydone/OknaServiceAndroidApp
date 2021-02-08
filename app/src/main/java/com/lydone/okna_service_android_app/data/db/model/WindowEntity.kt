package com.lydone.okna_service_android_app.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lydone.okna_service_android_app.data.db.converter.GlassUnitTypeConverter
import com.lydone.okna_service_android_app.data.db.converter.MaterialTypeConverter
import com.lydone.okna_service_android_app.data.db.converter.SashTypeListConverter
import com.lydone.okna_service_android_app.data.db.converter.WindowTypeConverter
import com.lydone.okna_service_android_app.domain.model.GlassUnitType
import com.lydone.okna_service_android_app.domain.model.MaterialType
import com.lydone.okna_service_android_app.domain.model.SashType
import com.lydone.okna_service_android_app.domain.model.WindowType

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
    val isWindowsillIncluded: Boolean,
    val isEbbIncluded: Boolean,
    val isSlopeIncluded: Boolean,
    val isLaminationIncluded: Boolean,
    val isMosquitoNetIncluded: Boolean,
)