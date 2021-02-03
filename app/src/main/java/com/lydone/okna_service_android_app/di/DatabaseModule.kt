package com.lydone.okna_service_android_app.di

import android.content.Context
import androidx.room.Room
import com.lydone.okna_service_android_app.data.db.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWindowEntityDao(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            CartDatabase::class.java,
            "cart",
        ).build().cartDao
}