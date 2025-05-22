package com.example.projectfigma.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projectfigma.Entites.Basket
import com.example.projectfigma.Entites.Dishes

@Dao
interface BasketDAO {

//    @Query("SELECT * FROM baskets")
//        fun getAll(): List<Basket>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Basket)

    @Update
    suspend fun update(item: Basket)
}