package com.lydone.okna_service_android_app.presentation.cart.converter

import androidx.annotation.StringRes
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.HouseType

object HouseTypeToStringResConverter {

    @StringRes
    fun convert(type: HouseType) = when (type) {
        HouseType.PREFAB -> R.string.prefab
        HouseType.BRICK -> R.string.brick
    }
}