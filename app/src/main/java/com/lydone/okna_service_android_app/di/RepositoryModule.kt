package com.lydone.okna_service_android_app.di

import com.lydone.okna_service_android_app.data.repository.*
import com.lydone.okna_service_android_app.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindsCalculatorRepository(impl: CalculatorRepositoryImpl): CalculatorRepository

    @Binds
    abstract fun bindsCartRepository(impl: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindsOrderRepository(impl: OrderRepositoryImpl): OrderRepository

    @Binds
    abstract fun bindsFirebaseMessagingRepository(impl: FirebaseMessagingRepositoryImpl): FirebaseMessagingRepository

    @Binds
    abstract fun bindsTokenRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository
}