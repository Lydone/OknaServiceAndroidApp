package com.lydone.okna_service_android_app.domain.calculator

import com.lydone.okna_service_android_app.models.data.TestBean
import retrofit2.Call

interface CalculatorRepository {
    fun testRequest(): Call<List<TestBean>>
}