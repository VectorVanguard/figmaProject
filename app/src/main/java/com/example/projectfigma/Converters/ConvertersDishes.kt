package com.example.projectfigma.Converters

import androidx.room.TypeConverter
import com.example.projectfigma.Entites.Dishes
import com.google.gson.Gson

class ConvertersDishes {
    private val gson = Gson()

    @TypeConverter
    fun fromDish(value: Dishes?): String? =
        value?.let { gson.toJson(it) }

    @TypeConverter
    fun toDish(value: String?): Dishes? =
        value?.let { gson.fromJson(it, Dishes::class.java) }
}