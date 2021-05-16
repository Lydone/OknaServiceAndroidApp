package com.lydone.okna_service_android_app.data.remote.converter

import com.google.common.truth.Truth
import com.lydone.okna_service_android_app.domain.model.SashType
import org.junit.Test

class SashTypeConverterTest {

    @Test
    fun fromType() {
        Truth.assertThat(SashTypeConverter.fromType(SashType.FIXED)).isEqualTo(1)
        Truth.assertThat(SashTypeConverter.fromType(SashType.SWING_OUT)).isEqualTo(2)
        Truth.assertThat(SashTypeConverter.fromType(SashType.SWING_OUT_AND_FLAP)).isEqualTo(3)
    }

    @Test
    fun toType() {
        Truth.assertThat(SashTypeConverter.toType(1)).isEqualTo(SashType.FIXED)
        Truth.assertThat(SashTypeConverter.toType(2)).isEqualTo(SashType.SWING_OUT)
        Truth.assertThat(SashTypeConverter.toType(3)).isEqualTo(SashType.SWING_OUT_AND_FLAP)
    }

    @Test(expected = IllegalArgumentException::class)
    fun toTypeThrows() {
        SashTypeConverter.toType(4)
    }

}