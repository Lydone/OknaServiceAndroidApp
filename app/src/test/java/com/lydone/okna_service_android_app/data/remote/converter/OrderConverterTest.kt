package com.lydone.okna_service_android_app.data.remote.converter

import com.google.common.truth.Truth
import com.lydone.okna_service_android_app.data.remote.model.OrderResponse
import com.lydone.okna_service_android_app.domain.model.Order
import org.junit.Test

class OrderConverterTest {

    @Test
    fun toModel() {
        Truth.assertThat(
            OrderConverter.toModel(
                OrderResponse(
                    ID,
                    DESCRIPTION,
                    PRICE,
                    emptyList(),
                    ADDRESS,
                    OrderResponse.Status.CREATED,
                    LATITUDE,
                    LONGITUDE
                )
            )
        ).isEqualTo(Order(ID, DESCRIPTION, PRICE, emptyList(), ADDRESS, Order.Status.CREATED, LATITUDE, LONGITUDE))
        Truth.assertThat(
            OrderConverter.toModel(
                OrderResponse(
                    ID,
                    DESCRIPTION,
                    PRICE,
                    emptyList(),
                    ADDRESS,
                    OrderResponse.Status.PREPAID,
                    LATITUDE,
                    LONGITUDE
                )
            )
        ).isEqualTo(Order(ID, DESCRIPTION, PRICE, emptyList(), ADDRESS, Order.Status.PREPAID, LATITUDE, LONGITUDE))
        Truth.assertThat(
            OrderConverter.toModel(
                OrderResponse(
                    ID,
                    DESCRIPTION,
                    PRICE,
                    emptyList(),
                    ADDRESS,
                    OrderResponse.Status.IN_WORK,
                    LATITUDE,
                    LONGITUDE
                )
            )
        ).isEqualTo(Order(ID, DESCRIPTION, PRICE, emptyList(), ADDRESS, Order.Status.IN_WORK, LATITUDE, LONGITUDE))
        Truth.assertThat(
            OrderConverter.toModel(
                OrderResponse(
                    ID,
                    DESCRIPTION,
                    PRICE,
                    emptyList(),
                    ADDRESS,
                    OrderResponse.Status.DONE,
                    LATITUDE,
                    LONGITUDE
                )
            )
        ).isEqualTo(Order(ID, DESCRIPTION, PRICE, emptyList(), ADDRESS, Order.Status.DONE, LATITUDE, LONGITUDE))
    }

    private companion object {
        private const val ID = 1
        private const val PRICE = 1
        private const val DESCRIPTION = ""
        private const val ADDRESS = ""
        private const val LATITUDE = 0.0
        private const val LONGITUDE = 0.0
    }
}