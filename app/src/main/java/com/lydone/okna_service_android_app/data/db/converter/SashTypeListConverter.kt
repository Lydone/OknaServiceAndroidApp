package com.lydone.okna_service_android_app.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lydone.okna_service_android_app.domain.model.SashType

class SashTypeListConverter {

    @TypeConverter
    fun fromList(list: List<SashType>) = Gson().toJson(list)

    @TypeConverter
    fun toList(json: String) = Gson().fromJson<List<SashType>>(json, object : TypeToken<List<SashType>>() {}.type)
}