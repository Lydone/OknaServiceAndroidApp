package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.db.CartDao
import com.lydone.okna_service_android_app.data.db.converter.WindowConverter
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartDao: CartDao) : CartRepository {

    override suspend fun addWindowToCart(window: Window) = cartDao.insertAll(WindowConverter.toEntity(window))

}