package com.lydone.okna_service_android_app.domain.repository

import com.lydone.okna_service_android_app.domain.model.HouseType
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.model.WindowDimensionsLimits
import com.lydone.okna_service_android_app.domain.model.WindowType

interface CalculatorRepository {

    suspend fun getWindowDimensionsLimits(): WindowDimensionsLimits

    suspend fun getMatchingWindowTypes(width: Int, height: Int): List<WindowType>

    suspend fun getPrice(
        window: Window,
        houseType: HouseType,
        isDeliveryIncluded: Boolean,
        isInstallationIncluded: Boolean
    ): Int
}