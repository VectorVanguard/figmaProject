package com.example.projectfigma.Entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "baskets")
data class Basket (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dishesIds: List<Int>
)
