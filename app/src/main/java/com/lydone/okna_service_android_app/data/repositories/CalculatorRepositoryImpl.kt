package com.lydone.okna_service_android_app.data.repositories

import com.lydone.okna_service_android_app.data.remote.CalculatorApiMapper
import com.lydone.okna_service_android_app.domain.calculator.CalculatorRepository
import com.lydone.okna_service_android_app.domain.calculator.data.WindowSizeLimits
import com.lydone.okna_service_android_app.models.data.TestBean
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowSashesCount
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

class CalculatorRepositoryImpl @Inject constructor(private val calculatorApiMapper: CalculatorApiMapper) :
    CalculatorRepository {
    override suspend fun testRequest(): List<TestBean> {
        val res = calculatorApiMapper.testRequest()
        return res.map { it }
    }

    override suspend fun getWindowSizeLimits(windowSashesCount: WindowSashesCount): WindowSizeLimits {
        TODO("Not yet implemented")
    }


}