package com.lydone.okna_service_android_app.presentation.cart.converter

import android.content.Context
import com.lydone.okna_service_android_app.domain.model.SashType
import java.util.*

object SashTypeListToStringConverter {

    fun convert(context: Context, list: List<SashType>) = buildString {
        for (i in 0 until list.size - 1) {
            append(
                "${
                    context.getString(SashTypeToStringResConverter.convert(list[i]))
                        .toLowerCase(Locale.getDefault())
                }, "
            )
        }
        append(
            context.getString(SashTypeToStringResConverter.convert(list.last()))
                .toLowerCase(Locale.getDefault())
        )
    }
}