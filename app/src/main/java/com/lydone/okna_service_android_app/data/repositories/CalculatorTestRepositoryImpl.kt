package com.lydone.okna_service_android_app.data.repositories

import com.lydone.okna_service_android_app.domain.calculator.CalculatorRepository
import com.lydone.okna_service_android_app.domain.calculator.model.WindowDimensionsLimits
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
}