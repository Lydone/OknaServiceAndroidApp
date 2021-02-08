package com.lydone.okna_service_android_app.presentation.cart.converter

import android.content.Context
import com.lydone.okna_service_android_app.R
import java.util.*

object OptionsToStringConverter {

    fun convert(
        context: Context,
        isWindowsillIncluded: Boolean,
        isEbbIncluded: Boolean,
        isSlopeIncluded: Boolean,
        isLaminationIncluded: Boolean,
        isMosquitoNetIncluded: Boolean
    ) =
        if (!isWindowsillIncluded && !isEbbIncluded && !isSlopeIncluded && !isLaminationIncluded && !isMosquitoNetIncluded) {
            context.getString(R.string.absent).toLowerCase(Locale.getDefault())
        } else {
            buildString {
                if (isWindowsillIncluded) {
                    append("${context.getString(R.string.windowsill).toLowerCase(Locale.getDefault())}, ")
                }
                if (isEbbIncluded) {
                    append("${context.getString(R.string.ebb).toLowerCase(Locale.getDefault())}, ")
                }
                if (isSlopeIncluded) {
                    append("${context.getString(R.string.slope).toLowerCase(Locale.getDefault())}, ")
                }
                if (isLaminationIncluded) {
                    append("${context.getString(R.string.lamination).toLowerCase(Locale.getDefault())}, ")
                }
                if (isMosquitoNetIncluded) {
                    append("${context.getString(R.string.mosquito_net).toLowerCase(Locale.getDefault())}, ")
                }
                deleteRange(startIndex = length - 2, endIndex = length)
            }
        }
}