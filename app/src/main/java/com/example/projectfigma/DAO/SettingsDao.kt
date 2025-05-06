package com.example.projectfigma.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectfigma.Entites.AppSettings

@Dao
interface SettingsDao {
    @Query("SELECT * FROM app_settings WHERE id = 0")
    suspend fun getSettings(): AppSettings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(settings: AppSettings)
}
