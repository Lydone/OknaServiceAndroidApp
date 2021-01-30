package com.lydone.okna_service_android_app.domain.calculator.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WindowModel(
    val width: Int,
    val height: Int,
    val materialType: MaterialType = MaterialType.BUDGET,
    val windowType: WindowType = WindowType.ONE_SASH,
    val sashes: List<SashType> = listOf(SashType.FIXED),
    val glassUnitType: GlassUnitType = GlassUnitType.SINGLE_CHAMBERED,
    val houseType: HouseType = HouseType.PREFAB,
    val isWindowsillSelected: Boolean = false,
    val isEbbSelected: Boolean = false,
    val isSlopeSelected: Boolean = false,
    val isLaminationSelected: Boolean = false,
    val isMosquitoNetSelected: Boolean = false
) : Parcelable