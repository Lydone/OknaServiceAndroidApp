package com.lydone.okna_service_android_app.data.remote.converter

import com.google.common.truth.Truth
import com.lydone.okna_service_android_app.data.remote.model.WindowDimensionsLimitsResponse
import com.lydone.okna_service_android_app.domain.model.WindowDimensionsLimits
import org.junit.Test

class WindowDimensionsLimitsConverterTest {

    @Test
    fun convert() {
        Truth.assertThat(
            WindowDimensionsLimitsConverter.convert(
                WindowDimensionsLimitsResponse(
                    1, 2, 3, 4
                )
            )
        ).isEqualTo(WindowDimensionsLimits(1, 3, 2, 4))
    }
}