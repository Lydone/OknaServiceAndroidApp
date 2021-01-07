package com.lydone.okna_service_android_app.di

import com.lydone.okna_service_android_app.data.remote.CalculatorApiMapper
import com.lydone.okna_service_android_app.data.repositories.CalculatorRepositoryImpl
import com.lydone.okna_service_android_app.domain.calculator.CalculatorRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideCalculatorApiMapper(): CalculatorApiMapper =
        Retrofit.Builder().baseUrl("http://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CalculatorApiMapper::class.java)
}