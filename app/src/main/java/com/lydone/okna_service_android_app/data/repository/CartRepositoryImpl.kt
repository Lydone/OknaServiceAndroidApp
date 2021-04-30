package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.db.CartDao
import com.lydone.okna_service_android_app.data.db.converter.WindowEntityConverter
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.repository.CartRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartDao: CartDao) : CartRepository {

    override fun getWindows() = cartDao.getAll().map { list -> list.map { WindowEntityConverter.toModel(it) } }

    override suspend fun addWindow(window: Window) = cartDao.insertAll(WindowEntityConverter.fromModel(window))

    override suspend fun deleteWindow(window: Window) = cartDao.delete(WindowEntityConverter.fromModel(window))

    override suspend fun updateWindow(window: Window) = cartDao.update(WindowEntityConverter.fromModel(window))

    override suspend fun getWindowById(id: Int) = WindowEntityConverter.toModel(cartDao.getById(id))

    override suspend fun deleteWindows() = cartDao.deleteAll()
}