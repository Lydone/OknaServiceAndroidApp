package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.model.HouseType
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.repository.CalculatorRepository
import com.lydone.okna_service_android_app.domain.repository.CartRepository
import javax.inject.Inject

class CartInteractor @Inject constructor(
    private val cartRepository: CartRepository,
    private val calculatorRepository: CalculatorRepository
    ) {

    fun getWindows() = cartRepository.getWindows()

    suspend fun deleteWindow(window: Window) = cartRepository.deleteWindow(window)

    suspend fun getPrice(
        window: Window,
        houseType: HouseType,
        isDeliveryIncluded: Boolean,
        isInstallationIncluded: Boolean
    ) = calculatorRepository.getPrice(window, houseType, isDeliveryIncluded, isInstallationIncluded)
}