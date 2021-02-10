package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.domain.model.HouseType
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.model.WindowDimensionsLimits
import com.lydone.okna_service_android_app.domain.model.WindowType
import com.lydone.okna_service_android_app.domain.repository.CalculatorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class CalculatorTestRepositoryImpl @Inject constructor() : CalculatorRepository {

    override suspend fun getWindowDimensionsLimits() = withContext(Dispatchers.IO) {
        delay(1000)
        WindowDimensionsLimits(900, 3000, 900, 2000)
    }

    override suspend fun getMatchingWindowTypes(width: Int, height: Int) = withContext(Dispatchers.IO) {
        delay(1000)
        listOf(WindowType.TWO_SASHES, WindowType.THREE_SASHES)
    }

    override suspend fun getPrice(
        window: Window,
        houseType: HouseType,
        isDeliveryIncluded: Boolean,
        isInstallationIncluded: Boolean
    ) = withContext(Dispatchers.IO) {
        delay(1000)
        Random.nextInt(100, 1000)
    }
}