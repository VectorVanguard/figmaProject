package com.example.projectfigma.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectfigma.Entites.Session

@Dao
interface SessionDao {
    @Query("SELECT * FROM session WHERE id = 0")
    suspend fun getSession(): Session?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(session: Session)

    @Query("DELETE FROM session")
    suspend fun clear()
}
