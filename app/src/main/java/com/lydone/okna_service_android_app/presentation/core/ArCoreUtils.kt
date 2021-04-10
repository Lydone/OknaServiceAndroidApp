package com.lydone.okna_service_android_app.presentation.core

import android.content.Context
import com.google.ar.core.ArCoreApk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun Context.isArCoreAvailable() = withContext(Dispatchers.IO) {
    with(ArCoreApk.getInstance()) {
        var availability: ArCoreApk.Availability
        do {
            availability = checkAvailability(this@isArCoreAvailable)
            delay(200)
        } while (availability.isTransient)
        availability.isSupported
    }

}