package com.example.projectfigma.Converters

import androidx.room.TypeConverter
import com.example.projectfigma.Entites.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SessionConverters {

    private val gson = Gson()

    @TypeConverter
    fun userToJson(user: User?): String? =
        user?.let { gson.toJson(it) }

    @TypeConverter
    fun jsonToUser(data: String?): User? =
        data?.let {
            val type = object : TypeToken<User>() {}.type
            gson.fromJson<User>(it, type)
        }
}