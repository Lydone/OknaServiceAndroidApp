package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.repository.CalculatorRepository
import com.lydone.okna_service_android_app.domain.repository.CartRepository
import javax.inject.Inject

class CalculatorInteractor @Inject constructor(
    private val calculatorRepository: CalculatorRepository,
    private val cartRepository: CartRepository
) {

    suspend fun getWindowSizeLimits(sashesCount: Int) = calculatorRepository.getWindowSizeLimits(sashesCount)

    suspend fun getOverallWindowDimensionsLimits() = calculatorRepository.getOverallWindowDimensionsLimits()

    suspend fun getMatchingWindowTypes(width: Int, height: Int) =
        calculatorRepository.getMatchingWindowTypes(width, height)

    suspend fun getPrice(window: Window, isDeliveryIncluded: Boolean, isInstallationIncluded: Boolean) =
        calculatorRepository.getPrice(window, isDeliveryIncluded, isInstallationIncluded)

    suspend fun addWindowToCart(window: Window) = cartRepository.addWindowToCart(window)
}