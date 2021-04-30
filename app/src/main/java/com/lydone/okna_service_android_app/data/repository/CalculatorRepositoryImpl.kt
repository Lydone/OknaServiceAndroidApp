package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.remote.ApiMapper
import com.lydone.okna_service_android_app.data.remote.converter.WindowDimensionsLimitsConverter
import com.lydone.okna_service_android_app.data.remote.converter.WindowDtoConverter
import com.lydone.okna_service_android_app.data.remote.converter.WindowTypeConverter
import com.lydone.okna_service_android_app.domain.model.HouseType
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.repository.CalculatorRepository
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(
    private val apiMapper: ApiMapper,
) : CalculatorRepository {

    override suspend fun getWindowDimensionsLimits() = WindowDimensionsLimitsConverter.convert(
        apiMapper.getWindowDimensionsLimits()
    )

    override suspend fun getMatchingWindowTypes(width: Int, height: Int) =
        apiMapper.getMatchingWindowTypes(
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
        apiMapper.getPrice(
            WindowDtoConverter.fromModel(
                window = window,
                houseType = houseType,
                isDeliveryIncluded = isDeliveryIncluded,
                isInstallationIncluded = isInstallationIncluded,
            )
        ).price
    )
}