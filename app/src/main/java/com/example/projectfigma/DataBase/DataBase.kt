package com.example.projectfigma.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projectfigma.DAO.BestSellerDao
import com.example.projectfigma.DAO.UserDao
import com.example.projectfigma.Entites.BestSeller
import com.example.projectfigma.Entites.User
import com.example.projectfigma.R
import com.example.projectfigma.Util.Converters
import java.util.concurrent.Executors
import kotlin.coroutines.coroutineContext

@Database (entities = [User::class, BestSeller::class], version = 2)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {

    abstract fun getUserDao() : UserDao
    abstract fun getBestSellerDao(): BestSellerDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
        fun ioThread(f: () -> Unit) = IO_EXECUTOR.execute(f)

        fun getDb(context: Context): DataBase {
            val appContext = context.applicationContext
            val packageName = appContext.packageName

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "app_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            ioThread {
                                getDb(appContext)
                                    .getBestSellerDao().insertAll(
                                    listOf(
                                        BestSeller(
                                            imageUri = "android.resource://$packageName/${R.drawable.best_seller_card_1}",
                                            price = 103.0
                                        ),
                                        BestSeller(
                                            imageUri = "android.resource://$packageName/${R.drawable.best_seller_card_2}",
                                            price = 50.0
                                        ),
                                        BestSeller(
                                            imageUri = "android.resource://$packageName/${R.drawable.best_seller_card_3}",
                                            price = 12.99
                                        ),
                                        BestSeller(
                                            imageUri = "android.resource://$packageName/${R.drawable.best_seller_card_4}",
                                            price = 8.20
                                        )
                                    )
                                )
                            }
                        }
                    })
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}