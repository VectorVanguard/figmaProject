package com.example.projectfigma.Entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey val id: Int = 0,       // единственная запись
    val isFirstRun: Boolean
)
