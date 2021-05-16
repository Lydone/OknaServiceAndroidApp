package com.lydone.okna_service_android_app.data.remote.converter

import com.google.common.truth.Truth
import com.lydone.okna_service_android_app.domain.model.WindowType
import org.junit.Test

class WindowTypeConverterTest {

    @Test
    fun fromType() {
        Truth.assertThat(WindowTypeConverter.fromType(WindowType.ONE_SASH)).isEqualTo(1)
        Truth.assertThat(WindowTypeConverter.fromType(WindowType.TWO_SASHES)).isEqualTo(2)
        Truth.assertThat(WindowTypeConverter.fromType(WindowType.THREE_SASHES)).isEqualTo(3)
    }

    @Test
    fun toType() {
        Truth.assertThat(WindowTypeConverter.toType(1)).isEqualTo(WindowType.ONE_SASH)
        Truth.assertThat(WindowTypeConverter.toType(2)).isEqualTo(WindowType.TWO_SASHES)
        Truth.assertThat(WindowTypeConverter.toType(3)).isEqualTo(WindowType.THREE_SASHES)
    }

    @Test(expected = IllegalArgumentException::class)
    fun toTypeThrows() {
        WindowTypeConverter.toType(4)
    }
}