package com.example.projectfigma.Entites

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Date
import java.time.LocalDateTime

@Parcelize
@Entity(tableName = "productInCart")
data class ProductInCart (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dish : Dishes,
    val creationTime: LocalDateTime,
    val quantity: Int = 1
) : Parcelable