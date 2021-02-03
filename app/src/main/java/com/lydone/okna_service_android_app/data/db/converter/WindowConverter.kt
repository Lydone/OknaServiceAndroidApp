package com.lydone.okna_service_android_app.data.db.converter

import com.lydone.okna_service_android_app.data.db.model.WindowEntity
import com.lydone.okna_service_android_app.domain.model.Window

object WindowConverter {

    fun toEntity(window: Window) = WindowEntity(
        width = window.width,
        height = window.height,
        materialType = window.materialType,
        windowType = window.windowType,
        sashes = window.sashes,
        glassUnitType = window.glassUnitType,
        houseType = window.houseType,
        isWindowsillIncluded = window.isWindowsillIncluded,
        isEbbIncluded = window.isEbbIncluded,
        isSlopeIncluded = window.isSlopeIncluded,
        isLaminationIncluded = window.isLaminationIncluded,
        isMosquitoNetIncluded = window.isMosquitoNetIncluded
    )
}