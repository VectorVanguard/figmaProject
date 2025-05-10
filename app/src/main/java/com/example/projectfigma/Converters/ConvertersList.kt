package com.example.projectfigma.Converters

import androidx.room.TypeConverter

class ConvertersList {
    @TypeConverter
    fun fromIntList(value: List<Int>?): String = value?.joinToString(",") ?: ""
    @TypeConverter
    fun toIntList(value: String): List<Int> =
        if (value.isEmpty()) emptyList() else value.split(",").map { it.toInt() }
}