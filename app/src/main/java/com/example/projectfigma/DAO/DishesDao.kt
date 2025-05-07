package com.example.projectfigma.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectfigma.Entites.Dishes

@Dao
interface DishesDao {
    @Query("SELECT * FROM best_seller")
    fun getAll(): List<Dishes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Dishes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Dishes>)

    @Query("SELECT * FROM best_seller")
    fun getAllV(): LiveData<List<Dishes>>

    @Query("SELECT * FROM best_seller b WHERE b.isBestSeller = 1")
    fun getBestSellers(): LiveData<List<Dishes>>

    @Query("SELECT * FROM best_seller b WHERE b.isRecommend = 1")
    fun getRecommend(): LiveData<List<Dishes>>
}