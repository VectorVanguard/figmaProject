package com.example.projectfigma.Entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class Session(
    @PrimaryKey val id: Int = 0,      // единственная запись
    val isLoggedIn: Boolean,
    val userEmail: String?, // email или null
    val user: User?
)
