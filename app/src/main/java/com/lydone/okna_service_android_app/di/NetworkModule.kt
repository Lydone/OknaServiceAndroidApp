package com.lydone.okna_service_android_app.di

import com.lydone.okna_service_android_app.data.remote.CalculatorApiMapper
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
    @Provides
    fun provideCalculatorApiMapper(): CalculatorApiMapper =
        Retrofit.Builder().baseUrl("https://okna-service-backend.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build())
            .build()
            .create(CalculatorApiMapper::class.java)
}