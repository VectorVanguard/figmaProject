package com.example.projectfigma.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projectfigma.Entites.Session
import com.example.projectfigma.Entites.User

@Dao
interface SessionDao {
    @Query("SELECT * FROM session WHERE id = 0")
    fun getSession(): Session?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(session: Session)

    @Query("DELETE FROM session")
    suspend fun clear()

    @Update
    fun updateSession(session: Session)
}
