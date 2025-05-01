package com.example.project.Repositories

import com.example.project.user.User
import com.example.project.user.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun authenticate(email: String, password: String): Boolean {
        return userDao.getUser(email, password) != null
    }

    suspend fun userExists(email: String): Boolean {
        return userDao.getUserByEmail(email) != null
    }

    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }
}
