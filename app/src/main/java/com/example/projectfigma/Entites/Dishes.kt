package com.example.projectfigma.Entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "best_seller")
data class Dishes(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val imageUri: String,
    val price: Double,
    val rating: Double,
    val isRecommend: Boolean,
    val isBestSeller: Boolean
)