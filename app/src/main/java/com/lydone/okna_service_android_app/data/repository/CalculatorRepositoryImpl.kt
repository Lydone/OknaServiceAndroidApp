package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.db.CartDao
import com.lydone.okna_service_android_app.data.remote.CalculatorApiMapper
import com.lydone.okna_service_android_app.data.remote.converter.GlassUnitTypeConverter
import com.lydone.okna_service_android_app.data.remote.converter.HouseTypeConverter
import com.lydone.okna_service_android_app.data.remote.converter.MaterialTypeConverter
import com.lydone.okna_service_android_app.data.remote.converter.SashTypeConverter
import com.lydone.okna_service_android_app.data.remote.model.CalculatorParamsDto
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.domain.model.WindowDimensionsLimits
import com.lydone.okna_service_android_app.domain.model.WindowType
import com.lydone.okna_service_android_app.domain.repository.CalculatorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(
    private val calculatorApiMapper: CalculatorApiMapper,
    private val cartDao: CartDao
) : CalculatorRepository {

    override suspend fun getWindowSizeLimits(sashesCount: Int) = withContext(Dispatchers.IO) {
        delay(1000)
        WindowDimensionsLimits(900, 3000, 900, 2000)
    }

    override suspend fun getOverallWindowDimensionsLimits() = withContext(Dispatchers.IO) {
        delay(1000)
        WindowDimensionsLimits(900, 3000, 900, 2000)
    }

    override suspend fun getMatchingWindowTypes(width: Int, height: Int) = withContext(Dispatchers.IO) {
        delay(1000)
        listOf(WindowType.TWO_SASHES, WindowType.THREE_SASHES)
    }

    override suspend fun getPrice(window: Window, isDeliveryIncluded: Boolean, isInstallationIncluded: Boolean) =
        requireNotNull(
            calculatorApiMapper.getPrice(
                CalculatorParamsDto(
                    width = window.width,
                    height = window.height,
                    materialType = MaterialTypeConverter.fromType(window.materialType),
                    sashes = window.sashes.map { SashTypeConverter.fromType(it) },
                    glassUnitType = GlassUnitTypeConverter.fromType(window.glassUnitType),
                    houseType = HouseTypeConverter.fromType(window.houseType),
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