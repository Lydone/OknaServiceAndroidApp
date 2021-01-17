package com.lydone.okna_service_android_app.data.repositories

import com.lydone.okna_service_android_app.data.remote.CalculatorApiMapper
import com.lydone.okna_service_android_app.domain.calculator.CalculatorRepository
import com.lydone.okna_service_android_app.domain.calculator.model.WindowSizeLimits
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(private val calculatorApiMapper: CalculatorApiMapper) :
    CalculatorRepository {

    override suspend fun getWindowSizeLimits(sashesCount: Int): WindowSizeLimits {
        TODO("Not yet implemented")
    }


}