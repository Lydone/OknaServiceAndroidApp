package com.lydone.okna_service_android_app.data.remote.converter

import com.lydone.okna_service_android_app.data.remote.model.WindowDto
import com.lydone.okna_service_android_app.domain.model.HouseType
import com.lydone.okna_service_android_app.domain.model.Window

object WindowDtoConverter {

    fun toModel(dto: WindowDto) = with(dto) {
        Window(
            width = requireNotNull(width),
            height = requireNotNull(height),
            materialType = MaterialTypeConverter.toType(requireNotNull(materialType)),
            windowType = WindowTypeConverter.toType(requireNotNull(sashes).size),
            sashes = sashes.map { SashTypeConverter.toType(it) },
            glassUnitType = GlassUnitTypeConverter.toType(requireNotNull(glassUnitType)),
            isWindowsillIncluded = requireNotNull(isWindowsillIncluded),
            isEbbIncluded = requireNotNull(isEbbIncluded),
            isSlopeIncluded = requireNotNull(isSlopeIncluded),
            isLaminationIncluded = requireNotNull(isLaminationIncluded),
            isMosquitoNetIncluded = requireNotNull(isMosquitoNetIncluded)
        )
    }

    fun fromModel(
        window: Window,
        houseType: HouseType,
        isDeliveryIncluded: Boolean,
        isInstallationIncluded: Boolean,
    ) = with(window) {
        WindowDto(
            width = width,
            height = height,
            materialType = MaterialTypeConverter.fromType(materialType),
            sashes = sashes.map { SashTypeConverter.fromType(it) },
            glassUnitType = GlassUnitTypeConverter.fromType(glassUnitType),
            houseType = HouseTypeConverter.fromType(houseType),
            isWindowsillIncluded = isWindowsillIncluded,
            isEbbIncluded = isEbbIncluded,
            isSlopeIncluded = isSlopeIncluded,
            isLaminationIncluded = isLaminationIncluded,
            isMosquitoNetIncluded = isMosquitoNetIncluded,
            isDeliveryIncluded = isDeliveryIncluded,
            isInstallationIncluded = isInstallationIncluded
        )
    }
}