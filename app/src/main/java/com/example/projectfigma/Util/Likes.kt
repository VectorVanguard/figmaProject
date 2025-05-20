package com.example.projectfigma.Util

import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.R

class Likes {

    private var dataBase: DataBase

    constructor(dataBase: DataBase){
        this.dataBase = dataBase
    }

    public fun likeDish(dish: Dishes, likeButton: ImageView) {

        val user = dataBase.getSessionDao().getSession()
            ?.let { it.userEmail?.let { it1 -> dataBase.getUserDao().getUserByEmail(it1) } }

        val favoriteDishes:List<Int> = user?.favoriteDishesId ?: emptyList()
        if (isLike(dish)) {
            val updatedDishes: List<Int> = favoriteDishes.filter { it != dish.id }
            dataBase.getUserDao().updateFavoriteDishes(user?.id!!, updatedDishes)
        } else {
            val updatedDishes = favoriteDishes + dish.id
            dataBase.getUserDao().updateFavoriteDishes(user?.id!!, updatedDishes)
        }
    }


    public fun isLike(dish: Dishes?): Boolean {
        val user = dataBase.getSessionDao().getSession()
            ?.let { it.userEmail?.let { it1 -> dataBase.getUserDao().getUserByEmail(it1) } }

        val favoriteDishes:List<Int> = user?.favoriteDishesId ?: emptyList()
        return favoriteDishes.contains(dish?.id)
    }

    public fun setLikeButton(likeButton: ImageView, dish: Dishes?) {
        if (isLike(dish)) {
            likeButton.setImageResource(R.drawable.ic_heart_border)
        } else {
            likeButton.setImageResource(R.drawable.ic_dislike)
        }
    }

}