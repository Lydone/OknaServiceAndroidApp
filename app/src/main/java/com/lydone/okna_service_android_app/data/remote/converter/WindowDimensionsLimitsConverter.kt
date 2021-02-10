package com.lydone.okna_service_android_app.data.remote.converter

import com.lydone.okna_service_android_app.data.remote.model.WindowDimensionsLimitsResponse
import com.lydone.okna_service_android_app.domain.model.WindowDimensionsLimits

object WindowDimensionsLimitsConverter {

    fun convert(response: WindowDimensionsLimitsResponse) = WindowDimensionsLimits(
        minWidth = requireNotNull(response.minWidth),
        maxWidth = requireNotNull(response.maxWidth),
        minHeight = requireNotNull(response.minHeight),
        maxHeight = requireNotNull(response.maxHeight)
    )
}