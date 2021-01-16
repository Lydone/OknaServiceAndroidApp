package com.lydone.okna_service_android_app.data.repositories

import com.lydone.okna_service_android_app.domain.calculator.CalculatorRepository
import com.lydone.okna_service_android_app.domain.calculator.data.WindowSizeLimits
import com.lydone.okna_service_android_app.models.data.TestBean
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowSashesCount
import javax.inject.Inject

class CalculatorTestRepositoryImpl @Inject constructor() : CalculatorRepository {
    override suspend fun testRequest(): List<TestBean> = emptyList()

    override suspend fun getWindowSizeLimits(windowSashesCount: WindowSashesCount) = when (windowSashesCount) {
        WindowSashesCount.ONE -> WindowSizeLimits(400, 3000, 400, 3000)
        WindowSashesCount.TWO -> WindowSizeLimits(900, 2000, 500, 2000)
        WindowSashesCount.THREE -> WindowSizeLimits(1400, 3000, 2000, 6000)
    }
}