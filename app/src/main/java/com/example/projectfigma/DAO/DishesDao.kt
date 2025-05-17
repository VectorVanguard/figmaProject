package com.example.projectfigma.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.Enums.DishCategory

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

    @Query("SELECT * FROM dishes d WHERE d.id = :id")
    fun getDishById(id : Int) : Dishes

    @Query("SELECT * FROM dishes WHERE id IN (:ids)")
    fun getDishesByIds(ids: List<Int>): List<Dishes>

    @Update
    suspend fun update(item: Dishes)

    //Сортировка по популярности (например, по рейтингу)
    @Query("SELECT * FROM dishes ORDER BY rating DESC")
    fun getSortedByRating(): LiveData<List<Dishes>>

    //Сортировка по цене (возрастание)
    @Query("SELECT * FROM dishes ORDER BY price ASC")
    fun getSortedByPriceAsc(): LiveData<List<Dishes>>

    //Сортировка по цене (убывание)
    @Query("SELECT * FROM dishes ORDER BY price DESC")
    fun getSortedByPriceDesc(): LiveData<List<Dishes>>

    @Query("SELECT * FROM Dishes ORDER BY rating DESC")
    suspend fun getAllSortedByRating(): List<Dishes>

    @Query("SELECT * FROM Dishes ORDER BY price ASC")
    suspend fun getAllSortedByPrice(): List<Dishes>

    @Query("SELECT * FROM Dishes ORDER BY ordersCount DESC")
    suspend fun getAllSortedByPopularity(): List<Dishes>


}