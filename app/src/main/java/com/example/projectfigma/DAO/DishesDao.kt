package com.example.projectfigma.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projectfigma.Entites.Dishes

@Dao
interface DishesDao {
    @Query("SELECT * FROM dishes")
    fun getAll(): List<Dishes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Dishes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Dishes>)

    @Query("SELECT * FROM dishes")
    fun getAllV(): LiveData<List<Dishes>>

    @Query("SELECT * FROM dishes b WHERE b.isBestSeller = 1")
    fun getBestSellers(): LiveData<List<Dishes>>

    @Query("SELECT * FROM dishes b WHERE b.isBestSeller = 1 LIMIT :limit")
    fun getBestSellersWithLimit(limit : Int): LiveData<List<Dishes>>

    @Query("SELECT * FROM dishes b WHERE b.isRecommend = 1")
    fun getRecommend(): LiveData<List<Dishes>>

    @Update
    suspend fun update(item: Dishes)
}