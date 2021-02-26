package com.lydone.okna_service_android_app.data.storage

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenSharedPreferencesStorage @Inject constructor(@ApplicationContext context: Context) {

    private val preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = preferences.getString(KEY_ACCESS_TOKEN, null)
        set(value) = preferences.edit(commit = true) { putString(KEY_ACCESS_TOKEN, value) }


    var refreshToken: String?
        get() = preferences.getString(KEY_REFRESH_TOKEN, null)
        set(value) = preferences.edit(commit = true) { putString(KEY_REFRESH_TOKEN, value) }

    private companion object {
        private const val KEY_ACCESS_TOKEN = "access"
        private const val KEY_REFRESH_TOKEN = "refresh"
    }
}