package com.example.projectfigma.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projectfigma.Entites.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(item: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM USERS")
    fun getAllUsers() : List<User>

    @Query("SELECT * FROM USERS u WHERE u.id = :id")
    fun getUserById(id: Int) : User?

    @Query("SELECT * FROM USERS u WHERE u.gmail LIKE :email")
    fun getUserByEmail(email : String) : User?
}