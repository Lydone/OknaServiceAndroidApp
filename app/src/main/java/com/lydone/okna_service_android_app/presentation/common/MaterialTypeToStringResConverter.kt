package com.lydone.okna_service_android_app.presentation.common

import androidx.annotation.StringRes
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.MaterialType

object MaterialTypeToStringResConverter {

    @StringRes
    fun convertToTitleString(materialType: MaterialType) = when (materialType) {
        MaterialType.BUDGET -> R.string.budget
        MaterialType.OPTIMUM -> R.string.optimum
        MaterialType.PREMIUM -> R.string.premium
    }

    @StringRes
    fun convertToDescriptionString(materialType: MaterialType) = when (materialType) {
        MaterialType.BUDGET -> R.string.budget_description
        MaterialType.OPTIMUM -> R.string.optimum_description
        MaterialType.PREMIUM -> R.string.premium_description
    }
}