package com.lydone.okna_service_android_app.domain.model

data class Window(
    val width: Int,
    val height: Int,
    val materialType: MaterialType,
    val windowType: WindowType,
    val sashes: List<SashType>,
    val glassUnitType: GlassUnitType,
    val houseType: HouseType,
    val isWindowsillIncluded: Boolean,
    val isEbbIncluded: Boolean,
    val isSlopeIncluded: Boolean,
    val isLaminationIncluded: Boolean,
    val isMosquitoNetIncluded: Boolean,
)