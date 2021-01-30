package com.lydone.okna_service_android_app.domain.calculator.model

data class CalculatorParams(
    val width: Int,
    val height: Int,
    val materialType: MaterialType,
    val windowType: WindowType,
    val sashes: List<SashType>,
    val glassUnitType: GlassUnitType,
    val houseType: HouseType,
    val isWindowsillChecked: Boolean,
    val isEbbChecked: Boolean,
    val isSlopeChecked: Boolean,
    val isLaminationChecked: Boolean,
    val isMosquitoNetChecked: Boolean,
    val isInstallationChecked: Boolean,
    val isDeliveryChecked: Boolean
)