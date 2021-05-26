package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.remote.ApiMapper
import com.lydone.okna_service_android_app.data.remote.converter.OrderConverter
import com.lydone.okna_service_android_app.data.remote.converter.WindowDtoConverter
import com.lydone.okna_service_android_app.data.remote.model.CreateOrderRequest
import com.lydone.okna_service_android_app.data.remote.model.PaymentUrlRequest
import com.lydone.okna_service_android_app.domain.model.CreateOrderParams
import com.lydone.okna_service_android_app.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiMapper: ApiMapper,
) : OrderRepository {

    override suspend fun createOrder(params: CreateOrderParams) = with(params) {
        apiMapper.createOrder(
            CreateOrderRequest(
                latitude = latitude,
                longitude = longitude,
                address = address,
                windows = windows.map { window ->
                    WindowDtoConverter.fromModel(
                        window = window,
                        houseType = params.houseType,
                        isDeliveryIncluded = params.isDeliveryIncluded,
                        isInstallationIncluded = params.isInstallationIncluded,
                    )
                }
            )
        ).let { OrderConverter.toModel(it) }
    }

    override suspend fun getOrders() = apiMapper.getOrders().let { response ->
        response.orders.map { OrderConverter.toModel(it) }
    }

    override suspend fun getPaymentUrl(orderId: Int, isPrepayment: Boolean) =
        requireNotNull(apiMapper.getPaymentUrl(PaymentUrlRequest(orderId, !isPrepayment)).url)
}