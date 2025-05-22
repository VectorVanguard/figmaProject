package com.example.projectfigma.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projectfigma.Entites.Address

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address: Address): Long

    @Update
    suspend fun update(address: Address)

    @Delete
    suspend fun delete(address: Address)

    @Query("SELECT * FROM address a WHERE a.userId = :userId")
    suspend fun getByUserId(userId: Int): List<Address>

    @Query("SELECT * FROM address a WHERE a.id = :id")
    suspend fun getById(id: Int): Address?

    @Query("UPDATE address SET isDefault = 0 WHERE userId = :userId")
    suspend fun clearDefaults(userId: Int)

    @Query("UPDATE address SET isDefault = 1 WHERE id = :addressId")
    suspend fun setAsDefault(addressId: Int)

    @Query("SELECT * FROM address WHERE userId = :userId AND isDefault = 1 LIMIT 1")
    suspend fun getDefaultAddress(userId: Int): Address?
}