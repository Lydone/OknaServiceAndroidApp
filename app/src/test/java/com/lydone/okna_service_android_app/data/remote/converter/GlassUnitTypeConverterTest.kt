package com.lydone.okna_service_android_app.data.remote.converter

import com.google.common.truth.Truth
import com.lydone.okna_service_android_app.domain.model.GlassUnitType
import org.junit.Test

class GlassUnitTypeConverterTest {

    @Test
    fun fromType() {
        Truth.assertThat(GlassUnitTypeConverter.fromType(GlassUnitType.SINGLE_CHAMBERED)).isEqualTo(1)
        Truth.assertThat(GlassUnitTypeConverter.fromType(GlassUnitType.DOUBLE_CHAMBERED)).isEqualTo(2)
    }

    @Test
    fun toType() {
        Truth.assertThat(GlassUnitTypeConverter.toType(1)).isEqualTo(GlassUnitType.SINGLE_CHAMBERED)
        Truth.assertThat(GlassUnitTypeConverter.toType(2)).isEqualTo(GlassUnitType.DOUBLE_CHAMBERED)
    }

    @Test(expected = IllegalArgumentException::class)
    fun toTypeThrows() {
        GlassUnitTypeConverter.toType(3)
    }
}