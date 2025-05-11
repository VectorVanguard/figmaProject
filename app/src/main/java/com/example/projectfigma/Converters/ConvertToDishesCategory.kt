package com.example.projectfigma.Converters

import androidx.room.TypeConverter
import com.example.projectfigma.Enums.DishCategory

class ConvertToDishesCategory {
    @TypeConverter
    fun fromCategory(cat: DishCategory): String = cat.name

    @TypeConverter
    fun toCategory(name: String): DishCategory = DishCategory.valueOf(name)
}