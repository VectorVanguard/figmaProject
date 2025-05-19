package com.example.projectfigma.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar
import com.example.projectfigma.databinding.ActivityFavoritesBinding
import com.example.projectfigma.databinding.ActivityFoodDetailBinding
import kotlin.time.toDuration

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var dataBase : DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBase = DataBase.getDb(this)

        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBar.hideStatusBar(window)

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()

        binding.exitArrow.setOnClickListener(){
            startActivity(Intent(this@FoodDetailActivity,HomeActivity::class.java))
        }

        val dish: Dishes? = intent.getParcelableExtra("dish")
        dish?.let {
            binding.nameDishHeader.text = it.name
            binding.tvRating.text = it.rating.toString()
            binding.textPrice.text = "$" + it.price.toString()
            Glide.with(binding.imgFood)
                .load(dish.imageUri)
                .into(binding.imgFood)

            binding.dishDescription.text = it.description
        }
    }

    private suspend fun likeDish(dish: Dishes) {

        val user = dataBase.getSessionDao().getSession()
            ?.let { it.userEmail?.let { it1 -> dataBase.getUserDao().getUserByEmail(it1) } }

        val favoriteDishes:List<Int> = user?.favoriteDishesId ?: emptyList<Int>()
        if (favoriteDishes.contains(dish.id)) {
            val updatedDishes: List<Int> = favoriteDishes.filter { it != dish.id }
            dataBase.getUserDao().updateFavoriteDishes(user?.id!!, updatedDishes)
        } else {
            val updatedDishes = favoriteDishes + dish.id
            dataBase.getUserDao().updateFavoriteDishes(user?.id!!, updatedDishes)
        }
    }
}