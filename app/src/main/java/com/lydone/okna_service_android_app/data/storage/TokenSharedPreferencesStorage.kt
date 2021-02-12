package com.lydone.okna_service_android_app.data.storage

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val KEY_SHORT_LIFE_TOKEN = "short"
private const val KEY_LONG_LIFE_TOKEN = "long"

class TokenSharedPreferencesStorage @Inject constructor(@ApplicationContext context: Context) {
    private val preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)

    var shortLifeToken: String?
        get() = preferences.getString(KEY_SHORT_LIFE_TOKEN, null)
        set(value) = preferences.edit(commit = true) { putString(KEY_SHORT_LIFE_TOKEN, value) }


    var longLifeToken: String?
        get() = preferences.getString(KEY_LONG_LIFE_TOKEN, null)
        set(value) = preferences.edit(commit = true) { putString(KEY_LONG_LIFE_TOKEN, value) }
}