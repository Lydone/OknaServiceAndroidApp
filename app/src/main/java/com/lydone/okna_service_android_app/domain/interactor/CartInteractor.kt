package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.repository.CartRepository
import javax.inject.Inject

class CartInteractor @Inject constructor(private val cartRepository: CartRepository) {

    fun getWindows() = cartRepository.getWindows()

    suspend fun deleteWindow(window: Window) = cartRepository.deleteWindow(window)
}