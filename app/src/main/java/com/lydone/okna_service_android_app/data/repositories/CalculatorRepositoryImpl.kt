package com.lydone.okna_service_android_app.data.repositories

import com.lydone.okna_service_android_app.data.remote.CalculatorApiMapper
import com.lydone.okna_service_android_app.domain.calculator.CalculatorRepository
import com.lydone.okna_service_android_app.domain.calculator.model.WindowDimensionsLimits
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(private val calculatorApiMapper: CalculatorApiMapper) :
    CalculatorRepository {

    override suspend fun getWindowSizeLimits(sashesCount: Int): WindowDimensionsLimits {
        TODO("Not yet implemented")
    }

    override suspend fun getOverallWindowDimensionsLimits(): WindowDimensionsLimits {
        TODO("Not yet implemented")
    }


}