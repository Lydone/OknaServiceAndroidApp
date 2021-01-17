package com.lydone.okna_service_android_app.presentation.calculator.converter

import androidx.annotation.IdRes
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.calculator.model.HouseType

object ChipIdToHouseTypeConverter {

    fun convert(@IdRes chipId: Int) = when (chipId) {
        R.id.prefab -> HouseType.PREFAB
        R.id.brick -> HouseType.BRICK
        else -> throw IllegalArgumentException("Incorrect chipId: $chipId")
    }

    @IdRes
    fun convertBack(houseType: HouseType) = when (houseType) {
        HouseType.PREFAB -> R.id.prefab
        HouseType.BRICK -> R.id.brick
    }
}