package com.example.projectfigma.Entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "password")
    var password : String,
    @ColumnInfo(name = "gmail")
    var gmail : String,
    @ColumnInfo(name = "mobileNumber")
    var mobileNumber : String,
    @ColumnInfo(name = "dateOfBirth")
    var dateOfBirth : Date,
    var favoriteDishesId: List<Int>
)
