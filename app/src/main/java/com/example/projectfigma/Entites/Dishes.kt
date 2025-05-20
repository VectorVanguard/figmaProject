package com.example.projectfigma.Entites

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.projectfigma.Enums.DishCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "dishes")
data class Dishes(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String,
    val price: Double,
    val rating: Double,
    val isRecommend: Boolean,
    val isBestSeller: Boolean,
    val name : String,
    val description : String,
    val category: DishCategory,
    val ordersCount: Int = 0
) : Parcelable