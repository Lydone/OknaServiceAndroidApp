package com.lydone.okna_service_android_app.data.db

import androidx.room.*
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

    @Update
    suspend fun update(windowEntity: WindowEntity)

    @Query("SELECT * FROM WindowEntity WHERE id = :id")
    suspend fun getById(id: Int): WindowEntity
}