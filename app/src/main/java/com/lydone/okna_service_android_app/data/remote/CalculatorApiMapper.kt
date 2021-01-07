package com.lydone.okna_service_android_app.data.remote

import com.lydone.okna_service_android_app.models.data.TestBean
import retrofit2.Call
import retrofit2.http.GET

interface CalculatorApiMapper {
    @GET("posts")
    fun testRequest(): Call<List<TestBean>>
}