package com.lydone.okna_service_android_app.data.db.converter

import com.lydone.okna_service_android_app.data.db.model.WindowEntity
import com.lydone.okna_service_android_app.domain.model.Window

object WindowEntityConverter {

    fun fromModel(window: Window) = window.id?.let { explicitId ->
        WindowEntity(
            id = explicitId,
            width = window.width,
            height = window.height,
            materialType = window.materialType,
            windowType = window.windowType,
            sashes = window.sashes,
            glassUnitType = window.glassUnitType,
            isWindowsillIncluded = window.isWindowsillIncluded,
            isEbbIncluded = window.isEbbIncluded,
            isSlopeIncluded = window.isSlopeIncluded,
            isLaminationIncluded = window.isLaminationIncluded,
            isMosquitoNetIncluded = window.isMosquitoNetIncluded
        )
    } ?: WindowEntity(
        width = window.width,
        height = window.height,
        materialType = window.materialType,
        windowType = window.windowType,
        sashes = window.sashes,
        glassUnitType = window.glassUnitType,
        isWindowsillIncluded = window.isWindowsillIncluded,
        isEbbIncluded = window.isEbbIncluded,
        isSlopeIncluded = window.isSlopeIncluded,
        isLaminationIncluded = window.isLaminationIncluded,
        isMosquitoNetIncluded = window.isMosquitoNetIncluded
    )

    fun toModel(windowEntity: WindowEntity) = Window(
        id = windowEntity.id,
        width = windowEntity.width,
        height = windowEntity.height,
        materialType = windowEntity.materialType,
        windowType = windowEntity.windowType,
        sashes = windowEntity.sashes,
        glassUnitType = windowEntity.glassUnitType,
        isWindowsillIncluded = windowEntity.isWindowsillIncluded,
        isEbbIncluded = windowEntity.isEbbIncluded,
        isSlopeIncluded = windowEntity.isSlopeIncluded,
        isLaminationIncluded = windowEntity.isLaminationIncluded,
        isMosquitoNetIncluded = windowEntity.isMosquitoNetIncluded
    )
}