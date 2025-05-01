package com.example.project.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String,
    val dateOfBirth: String
)