package com.lydone.okna_service_android_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lydone.okna_service_android_app.data.db.model.WindowEntity

@Database(entities = [WindowEntity::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract val cartDao: CartDao
}