package com.example.projectfigma.Entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class Address(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "userId", index = true)
    var userId: Int,
    @ColumnInfo(name = "name")
    var name: String, // Название адреса (Дом, Работа и т.д.)
    @ColumnInfo(name = "address")
    var address: String, // Полный адрес в виде строки
    @ColumnInfo(name = "isDefault")
    var isDefault: Boolean = false
)