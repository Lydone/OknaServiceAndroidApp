package com.lydone.okna_service_android_app.di

import com.google.gson.GsonBuilder
import com.lydone.okna_service_android_app.data.remote.ApiMapper
import com.lydone.okna_service_android_app.data.storage.TokenSharedPreferencesStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val BASE_URL = "https://okna-service-backend.herokuapp.com/api/"

    @Provides
    fun providesCalculatorApiMapper(tokenSharedPreferencesStorage: TokenSharedPreferencesStorage): ApiMapper =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(
                            tokenSharedPreferencesStorage.accessToken?.let { token ->
                                chain.request().newBuilder().apply {
                                    addHeader("Authorization", "Bearer $token")
                                }.build()
                            } ?: chain.request()
                        )
                    }
//                    .addInterceptor { chain ->
//                        val response = chain.proceed(chain.request())
//                        if (response.code == 403) {
//                            chain.proceed(
//                                Request.Builder()
//                                    .url(BASE_URL + "auth/refreshToken")
//                                    .post(Gson().toJson(RefreshTokenRequest(tokenRepository.refreshToken!!)).toRequestBody())
//                                    .build()
//                            )
//                        } else {
//                            response
//                        }
//                    }
                    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                    .build()
            )
            .build()
            .create(ApiMapper::class.java)
}