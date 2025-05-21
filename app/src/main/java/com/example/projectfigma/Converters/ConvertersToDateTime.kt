package com.example.projectfigma.Converters

import androidx.room.TypeConverter
import com.example.projectfigma.Enums.DishCategory
import java.time.LocalDateTime
import java.util.Date

class ConvertersToDateTime {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromDateTime(value: LocalDateTime?): String? =
        value?.toString()

    @TypeConverter
    fun toDateTime(value: String?): LocalDateTime? =
        value?.let { LocalDateTime.parse(it) }
}