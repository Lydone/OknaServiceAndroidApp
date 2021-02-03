package com.lydone.okna_service_android_app.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lydone.okna_service_android_app.data.db.model.WindowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM WindowEntity")
    fun getAll(): Flow<List<WindowEntity>>

    @Insert
    suspend fun insertAll(vararg windows: WindowEntity)

    @Delete
    suspend fun delete(windowEntity: WindowEntity)
}