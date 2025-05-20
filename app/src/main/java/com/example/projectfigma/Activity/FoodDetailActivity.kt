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
import com.example.projectfigma.Util.Likes
import com.example.projectfigma.Util.StatusBar
import com.example.projectfigma.databinding.ActivityFavoritesBinding
import com.example.projectfigma.databinding.ActivityFoodDetailBinding
import kotlin.time.toDuration

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var dataBase : DataBase
    private lateinit var likes: Likes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBase = DataBase.getDb(this)
        likes = Likes(dataBase)

        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBar.hideStatusBar(window)

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()

        binding.exitArrow.setOnClickListener(){
            startActivity(Intent(this@FoodDetailActivity,HomeActivity::class.java))
        }

        val dish = intent.getParcelableExtra<Dishes>("dish")

        if(likes.isLike(dish)){
            likes.setLikeButton(binding.likeButton, dish)
        }

        binding.likeButton.setOnClickListener(){
            if (dish != null) {
                likes.likeDish(dish,binding.likeButton)
            }
        }

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
}