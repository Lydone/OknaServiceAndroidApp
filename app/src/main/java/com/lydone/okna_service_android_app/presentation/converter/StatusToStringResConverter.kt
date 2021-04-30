package com.lydone.okna_service_android_app.presentation.converter

import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.Order

object StatusToStringResConverter {

    fun convert(status: Order.Status) = when (status) {
        Order.Status.CREATED -> R.string.created
        Order.Status.PREPAID -> R.string.prepaid
        Order.Status.IN_WORK -> R.string.in_work
        Order.Status.DONE -> R.string.done
    }
}