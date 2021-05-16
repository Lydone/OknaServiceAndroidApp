package com.lydone.okna_service_android_app.data.remote.converter

import com.google.common.truth.Truth
import com.lydone.okna_service_android_app.domain.model.MaterialType
import org.junit.Test

class MaterialTypeConverterTest {

    @Test
    fun fromType() {
        Truth.assertThat(MaterialTypeConverter.fromType(MaterialType.BUDGET)).isEqualTo(1)
        Truth.assertThat(MaterialTypeConverter.fromType(MaterialType.OPTIMUM)).isEqualTo(2)
        Truth.assertThat(MaterialTypeConverter.fromType(MaterialType.PREMIUM)).isEqualTo(3)
    }

    @Test
    fun toType() {
        Truth.assertThat(MaterialTypeConverter.toType(1)).isEqualTo(MaterialType.BUDGET)
        Truth.assertThat(MaterialTypeConverter.toType(2)).isEqualTo(MaterialType.OPTIMUM)
        Truth.assertThat(MaterialTypeConverter.toType(3)).isEqualTo(MaterialType.PREMIUM)
    }

    @Test(expected = IllegalArgumentException::class)
    fun toTypeThrows() {
        MaterialTypeConverter.toType(4)
    }
}