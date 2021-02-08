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

    override suspend fun getWindowSizeLimits(sashesCount: Int) = when (sashesCount) {
        1 -> WindowDimensionsLimits(400, 3000, 400, 3000)
        2 -> WindowDimensionsLimits(900, 2000, 500, 2000)
        3 -> WindowDimensionsLimits(1400, 3000, 600, 3000)
        else -> throw IllegalArgumentException("Incorrect sashes count: $sashesCount")
    }

    override suspend fun getOverallWindowDimensionsLimits() = withContext(Dispatchers.IO) {
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