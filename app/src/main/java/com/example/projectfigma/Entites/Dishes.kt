package com.example.projectfigma.Entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.projectfigma.Enums.DishCategory

@Entity(tableName = "dishes")
data class Dishes(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val imageUri: String,
    val price: Double,
    val rating: Double,
    val isRecommend: Boolean,
    val isBestSeller: Boolean,
    val name : String,
    val description : String,
    val category: String
)