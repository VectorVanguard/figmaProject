package com.example.projectfigma.DataBase

import android.content.Context
import android.text.style.TtsSpan
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projectfigma.Converters.ConvertToDishesCategory
import com.example.projectfigma.Converters.ConvertersList
import com.example.projectfigma.DAO.DishesDao
import com.example.projectfigma.DAO.SettingsDao
import com.example.projectfigma.DAO.SessionDao
import com.example.projectfigma.DAO.UserDao
import com.example.projectfigma.Entites.AppSettings
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.Entites.Session
import com.example.projectfigma.Entites.User
import com.example.projectfigma.Enums.DishCategory
import com.example.projectfigma.R
import com.example.projectfigma.Converters.ConvertersToDateTime
import com.example.projectfigma.Converters.SessionConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Date

@Database(
    entities = [User::class, Dishes::class, Session::class, AppSettings::class],
    version = 10
)
@TypeConverters(
    ConvertersToDateTime::class,
    ConvertToDishesCategory::class,
    ConvertersList::class,
    SessionConverters::class
)
abstract class DataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getDishesDao(): DishesDao
    abstract fun getSessionDao(): SessionDao
    abstract fun getSettingsDao(): SettingsDao
    abstract fun getBasketDao(): BasketDAO

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        private fun prepopulateUsers(): List<User> = listOf(
            User(
                name = "TestUser",
                gmail = "test@yandex.ru",
                password = "1111",
                mobileNumber = "818412481",
                dateOfBirth = Date(),
                favoriteDishesId = listOf(1,2,3)
            )
        )

        private fun prepopulateBestSellers(packageName: String) = listOf(
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.best_seller_card_1}",
                price = 103.0,
                isBestSeller = true,
                isRecommend = false,
                rating = 3.0,
                name = "Суши",
                description = "Суши",
                category = DishCategory.MEAL
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.best_seller_card_2}",
                price = 50.0,
                isBestSeller = true,
                isRecommend = false,
                rating = 4.0,
                name = "Рис с курицей",
                description = "Рис с курицей в подливе",
                category = DishCategory.MEAL
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.best_seller_card_3}",
                price = 12.99,
                isBestSeller = true,
                isRecommend = false,
                rating = 4.0,
                name = "Лазанья",
                description = "Лазанья",
                category = DishCategory.VEGAN
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.best_seller_card_4}",
                price = 8.20,
                isBestSeller = true,
                isRecommend = false,
                rating = 4.0,
                name = "Пироженное",
                description = "Пироженное",
                category = DishCategory.DESERT
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.burger}",
                price = 10.0,
                isBestSeller = false,
                isRecommend = true,
                rating = 5.0,
                name = "Бургер",
                description = "Бургер",
                category = DishCategory.MEAL
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.roll}",
                price = 25.0,
                isBestSeller = false,
                isRecommend = true,
                rating = 5.0,
                name = "Роллы",
                description = "Роллы",
                category = DishCategory.VEGAN
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.roll}",
                price = 25.0,
                isBestSeller = false,
                isRecommend = true,
                rating = 5.0,
                name = "Роллы",
                description = "Роллы",
                category = DishCategory.VEGAN
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.into_best_seller1}",
                price = 15.0,
                isBestSeller = true,
                isRecommend = false,
                rating = 5.0,
                name = "Солнечная брускетта",
                description = "Солнечная брускетта",
                category = DishCategory.SNACKS
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.into_best_seller2}",
                price = 12.0,
                isBestSeller = true,
                isRecommend = false,
                rating = 4.5,
                name = "Изысканные шашлыки на гриле",
                description = "Изысканные шашлыки на гриле",
                category = DishCategory.SNACKS
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.into_best_seller3}",
                price = 15.0,
                isBestSeller = true,
                isRecommend = false,
                rating = 4.0,
                name = "Тако с барбекю",
                description = "Тако с барбекю",
                category = DishCategory.MEAL
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.into_best_seller4}",
                price = 12.0,
                isBestSeller = true,
                isRecommend = false,
                rating = 3.5,
                name = "Лазанья из брокколи",
                description = "Лазанья из брокколи",
                category = DishCategory.VEGAN
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.into_best_seller5}",
                price = 15.0,
                isBestSeller = true,
                isRecommend = false,
                rating = 3.0,
                name = "Снежный кофе",
                description = "Снежный кофе",
                category = DishCategory.DRINKS
            ),
            Dishes(
                imageUri = "android.resource://$packageName/${R.drawable.into_best_seller6}",
                price = 12.0,
                isBestSeller = true,
                isRecommend = false,
                rating = 4.0,
                name = "Клубничный чизкейк",
                description = "Клубничный чизкейк",
                category = DishCategory.DESERT
            ),
        )

        fun getDb(context: Context): DataBase {
            val appContext = context.applicationContext
            val pkg = appContext.packageName

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    DataBase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            applicationScope.launch {
                                val database = getDb(appContext)

                                val dishesDao = database.getDishesDao()
                                if (dishesDao.getAll().isEmpty()) {
                                    dishesDao.insertAll(prepopulateBestSellers(pkg))
                                }

                                val userDao = database.getUserDao()
                                if (userDao.getAllUsers().isEmpty()) {
                                    userDao.insertAll(prepopulateUsers())
                                }

                                val settingsDao = database.getSettingsDao()
                                settingsDao.upsert(AppSettings(id = 0, isFirstRun = true))

                                val sessionDao = database.getSessionDao()
                                sessionDao.upsert(Session(id = 0, isLoggedIn = false, userEmail = null, user = null))
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
