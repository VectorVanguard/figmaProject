package com.example.projectfigma.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projectfigma.Entites.Basket
import com.example.projectfigma.Entites.ProductInCart

@Dao
interface ProductInCartDAO {
    @Query("SELECT * FROM productInCart")
    fun getAll(): List<ProductInCart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ProductInCart)

    @Update
    suspend fun update(item: ProductInCart)
}