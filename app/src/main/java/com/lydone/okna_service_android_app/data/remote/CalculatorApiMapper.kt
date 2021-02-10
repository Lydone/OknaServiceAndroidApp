package com.lydone.okna_service_android_app.data.remote

import com.lydone.okna_service_android_app.data.remote.model.CalculatorParamsDto
import com.lydone.okna_service_android_app.data.remote.model.PriceResponse
import com.lydone.okna_service_android_app.data.remote.model.WindowDimensionsLimitsResponse
import com.lydone.okna_service_android_app.data.remote.model.WindowTypeDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CalculatorApiMapper {

    @POST("calculator/calculate")
    suspend fun getPrice(@Body calculatorParamsDto: CalculatorParamsDto): PriceResponse

    @GET("calculator/getstart")
    suspend fun getWindowDimensionsLimits(): WindowDimensionsLimitsResponse

    @POST("calculator/getsashes")
    suspend fun getMatchingWindowTypes(
        @Query("Height") height: Int, @Query("Width") width: Int
    ): List<WindowTypeDto>
}