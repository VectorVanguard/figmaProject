package com.example.projectfigma.Util

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.viewbinding.ViewBinding
import com.example.projectfigma.Activity.FoodDetailActivity
import com.example.projectfigma.Entites.Dishes
import java.util.Objects

class SwitchCard {
    companion object {
        @JvmStatic
        fun switchDish(item: Dishes, context: Context, cls: Class<*>) {
            val intent = Intent(context, cls).apply {
                putExtra("dish", item)
            }
            context.startActivity(intent)
        }
    }
}