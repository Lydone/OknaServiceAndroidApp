package com.lydone.okna_service_android_app.data.remote

import com.lydone.okna_service_android_app.data.remote.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiMapper {

    @POST("calculator/calculate")
    suspend fun getPrice(@Body windowDto: WindowDto): PriceResponse

    @GET("calculator/getstart")
    suspend fun getWindowDimensionsLimits(): WindowDimensionsLimitsResponse

    @POST("calculator/getsashes")
    suspend fun getMatchingWindowTypes(
        @Query("Height") height: Int, @Query("Width") width: Int
    ): List<WindowTypeDto>

    @POST("auth/sendCode")
    suspend fun sendSmsCode(@Body phoneNumberDto: PhoneNumberDto)

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): TokensDto

    @POST("auth/registerCustomer")
    suspend fun signUp(@Body request: SignUpRequest): TokensDto

    @POST("auth/refreshToken")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): TokensDto

    @GET("auth/me")
    suspend fun getUserInfo(): UserInfoDto

    @POST("orders")
    suspend fun createOrder(@Body request: CreateOrderRequest): OrderResponse

    @GET("orders")
    suspend fun getOrders(): OrdersResponse

    @POST("payments/create")
    suspend fun getPaymentUrl(@Body request: PaymentUrlRequest): PaymentUrlResponse
}