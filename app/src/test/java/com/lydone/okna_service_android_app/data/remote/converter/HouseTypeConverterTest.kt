package com.lydone.okna_service_android_app.data.remote.converter

import com.google.common.truth.Truth
import com.lydone.okna_service_android_app.domain.model.HouseType
import org.junit.Test

class HouseTypeConverterTest {

    @Test
    fun fromType() {
        Truth.assertThat(HouseTypeConverter.fromType(HouseType.PREFAB)).isEqualTo(1)
        Truth.assertThat(HouseTypeConverter.fromType(HouseType.BRICK)).isEqualTo(2)
    }

    @Test
    fun toType() {
        Truth.assertThat(HouseTypeConverter.toType(1)).isEqualTo(HouseType.PREFAB)
        Truth.assertThat(HouseTypeConverter.toType(2)).isEqualTo(HouseType.BRICK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun toTypeThrows() {
        HouseTypeConverter.toType(3)
    }
}