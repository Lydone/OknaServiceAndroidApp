package com.lydone.okna_service_android_app.data.remote.converter

import com.google.common.truth.Truth
import com.lydone.okna_service_android_app.data.remote.model.WindowDto
import com.lydone.okna_service_android_app.domain.model.*
import org.junit.Test

class WindowDtoConverterTest {

    @Test
    fun toModel() {
        Truth.assertThat(
            WindowDtoConverter.toModel(
                WindowDto(
                    1, 1, 1, listOf(1), 1, 1, false, false, false, false, false, false, false
                )
            )
        ).isEqualTo(
            Window(
                null,
                1,
                1,
                MaterialType.BUDGET,
                WindowType.ONE_SASH,
                listOf(SashType.FIXED),
                GlassUnitType.SINGLE_CHAMBERED,
                false,
                false,
                false,
                false,
                false
            )
        )
    }

    @Test
    fun fromModel() {
        Truth.assertThat(
            WindowDtoConverter.fromModel(
                Window(
                    null,
                    1,
                    1,
                    MaterialType.BUDGET,
                    WindowType.ONE_SASH,
                    listOf(SashType.FIXED),
                    GlassUnitType.SINGLE_CHAMBERED,
                    false,
                    false,
                    false,
                    false,
                    false
                ),
                HouseType.PREFAB,
                false,
                false
            )
        ).isEqualTo(
            WindowDto(
                1, 1, 1, listOf(1), 1, 1, false, false, false, false, false, false, false
            )
        )
    }
}