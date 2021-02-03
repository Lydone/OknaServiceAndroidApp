package com.lydone.okna_service_android_app.data.remote

import com.lydone.okna_service_android_app.data.remote.model.CalculatorParamsDto
import com.lydone.okna_service_android_app.data.remote.model.PriceResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CalculatorApiMapper {

    @POST("calculator/calculate")
    suspend fun getPrice(@Body calculatorParamsDto: CalculatorParamsDto): PriceResponse
}