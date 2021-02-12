package com.lydone.okna_service_android_app.di

import com.lydone.okna_service_android_app.data.repository.CalculatorRepositoryImpl
import com.lydone.okna_service_android_app.data.repository.CartRepositoryImpl
import com.lydone.okna_service_android_app.data.repository.LoginRepositoryImpl
import com.lydone.okna_service_android_app.data.repository.OrderRepositoryImpl
import com.lydone.okna_service_android_app.domain.repository.CalculatorRepository
import com.lydone.okna_service_android_app.domain.repository.CartRepository
import com.lydone.okna_service_android_app.domain.repository.LoginRepository
import com.lydone.okna_service_android_app.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindsCalculatorRepository(repository: CalculatorRepositoryImpl): CalculatorRepository

    @Binds
    abstract fun bindsCartRepository(repository: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindsOrderRepository(repository: OrderRepositoryImpl): OrderRepository

    @Binds
    abstract fun bindsLoginRepository(repository: LoginRepositoryImpl): LoginRepository
}