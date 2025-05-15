package com.example.projectfigma.Activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var imgDish: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvRating: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        imgDish = findViewById(R.id.imgDish)
        tvName = findViewById(R.id.tvName)
        tvDescription = findViewById(R.id.tvDescription)
        tvPrice = findViewById(R.id.tvPrice)
        tvRating = findViewById(R.id.tvRating)

        val dishId = intent.getIntExtra("dish_id", -1)
        if (dishId != -1) {
            loadDish(dishId)
        }
    }

    private fun loadDish(id: Int) {
        val dao = DataBase.getDb(this).getDishesDao()

        CoroutineScope(Dispatchers.IO).launch {
            val dish = dao.getDishById(id)
            withContext(Dispatchers.Main) {
                dish?.let {
                    tvName.text = it.name
                    tvDescription.text = it.description
                    tvPrice.text = "$${"%.2f".format(it.price)}"
                    tvRating.text = "★ ${it.rating}"
                    Glide.with(this@FoodDetailActivity).load(it.imageUri).into(imgDish)
                }
            }
        }
    }
}
