package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.db.CartDao
import com.lydone.okna_service_android_app.data.remote.CalculatorApiMapper
import com.lydone.okna_service_android_app.data.remote.converter.*
import com.lydone.okna_service_android_app.data.remote.model.CalculatorParamsDto
import com.lydone.okna_service_android_app.domain.model.HouseType
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.repository.CalculatorRepository
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(
    private val calculatorApiMapper: CalculatorApiMapper,
    private val cartDao: CartDao
) : CalculatorRepository {

    override suspend fun getWindowDimensionsLimits() = WindowDimensionsLimitsConverter.convert(
        calculatorApiMapper.getWindowDimensionsLimits()
    )

    override suspend fun getMatchingWindowTypes(width: Int, height: Int) =
        calculatorApiMapper.getMatchingWindowTypes(
            width = width,
            height = height
        ).filter { it.windowType in 1..3 }
            .map { WindowTypeConverter.toType(requireNotNull(it.windowType)) }
            .sorted()

    override suspend fun getPrice(
        window: Window,
        houseType: HouseType,
        isDeliveryIncluded: Boolean,
        isInstallationIncluded: Boolean
    ) = requireNotNull(
        calculatorApiMapper.getPrice(
            CalculatorParamsDto(
                width = window.width,
                height = window.height,
                materialType = MaterialTypeConverter.fromType(window.materialType),
                sashes = window.sashes.map { SashTypeConverter.fromType(it) },
                glassUnitType = GlassUnitTypeConverter.fromType(window.glassUnitType),
                houseType = HouseTypeConverter.fromType(houseType),
                isWindowsillIncluded = window.isWindowsillIncluded,
                isSlopeIncluded = window.isSlopeIncluded,
                isEbbIncluded = window.isEbbIncluded,
                isLaminationIncluded = window.isLaminationIncluded,
                isMosquitoNetIncluded = window.isMosquitoNetIncluded,
                isDeliveryIncluded = isDeliveryIncluded,
                isInstallationIncluded = isInstallationIncluded
            )
        ).price
    )
}