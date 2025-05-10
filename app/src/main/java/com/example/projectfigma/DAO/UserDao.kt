package com.example.projectfigma.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.Entites.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(item: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<User>)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM USERS")
    fun getAllUsers() : List<User>

    @Query("SELECT * FROM USERS u WHERE u.id = :id")
    fun getUserById(id: Int) : User?

    @Query("SELECT * FROM USERS u WHERE u.gmail LIKE :email")
    fun getUserByEmail(email : String) : User
}