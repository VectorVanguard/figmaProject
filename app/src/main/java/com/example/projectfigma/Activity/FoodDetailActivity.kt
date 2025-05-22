package com.example.projectfigma.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.Entites.ProductInCart
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.Likes
import com.example.projectfigma.Util.StatusBar
import com.example.projectfigma.databinding.ActivityFavoritesBinding
import com.example.projectfigma.databinding.ActivityFoodDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import kotlin.time.toDuration

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var likes: Likes

    private val db                by lazy { DataBase.getDb(this) }
    private val cartDao           by lazy { db.getProductInCartDAO() }
    private var dish: Dishes?     = null
    private var quantity: Int     = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        likes = Likes(db)

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

        binding.btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
                updateTotalPrice()
            }
        }
        binding.btnPlus.setOnClickListener {
            quantity++
            binding.tvQuantity.text = quantity.toString()
            updateTotalPrice()
        }

        binding.addToCartButton.setOnClickListener {
            dish?.let { d ->
                addToCart(d)
            }
        }
    }

    private fun updateTotalPrice() {
        val dish = intent.getParcelableExtra<Dishes>("dish")
        val price  = dish?.price ?: 0.0
        val total  = price * quantity
        binding.textPrice.text = "$${"%.2f".format(total)}"
    }

    private fun addToCart(d: Dishes) {
        // Создаем ProductInCart
        val product = ProductInCart(
            dish         = d,
            creationTime = LocalDateTime.now(),
            quantity     = quantity
        )

        // Сохраняем в БД
        lifecycleScope.launch(Dispatchers.IO) {
            cartDao.insert(product)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@FoodDetailActivity,
                    "Добавлено в корзину: ${d.name} ×$quantity",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}
