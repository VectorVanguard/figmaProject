package com.example.projectfigma.Enums

import androidx.annotation.DrawableRes
import com.example.projectfigma.R

enum class DishCategory(val displayName: String, @DrawableRes val iconRes: Int) {
    MEAL("Обычная", R.drawable.ic_meal),
    VEGAN("Вегатарианская", R.drawable.ic_vegan),
    SNACKS("Снэки", R.drawable.ic_snacks),
    DESERT("Десерт", R.drawable.ic_dessert),
    DRINKS("Напиток", R.drawable.ic_drinks)
}