package com.example.projectfigma.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projectfigma.DAO.UserDao
import com.example.projectfigma.Entites.User
import com.example.projectfigma.Util.Converters

@Database (entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {

    abstract fun getUserDao() : UserDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDb(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}