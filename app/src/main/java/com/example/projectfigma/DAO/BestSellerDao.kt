package com.example.projectfigma.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectfigma.Entites.BestSeller

@Dao
interface BestSellerDao {
    @Query("SELECT * FROM best_seller")
    fun getAll(): List<BestSeller>

    // Вставить один объект
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BestSeller)

    // Вставить список объектов
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<BestSeller>)

    @Query("SELECT * FROM best_seller")
    fun getAllV(): LiveData<List<BestSeller>>
}