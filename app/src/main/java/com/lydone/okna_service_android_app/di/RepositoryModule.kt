package com.lydone.okna_service_android_app.di

import com.lydone.okna_service_android_app.data.repositories.CalculatorRepositoryImpl
import com.lydone.okna_service_android_app.domain.calculator.CalculatorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsCalculatorRepository(calculatorRepositoryImpl: CalculatorRepositoryImpl): CalculatorRepository
}