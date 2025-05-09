package com.example.projectfigma.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.R

class FavoriteFoodAdapter(
    private val items: MutableList<Dishes>
) : RecyclerView.Adapter<FavoriteFoodAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val imgFood: ImageView    = view.findViewById(R.id.imgFood)
        val imgTag: ImageView     = view.findViewById(R.id.imgTag)
        val imgFavorite: ImageView= view.findViewById(R.id.imgFavorite)
        val tvTitle: TextView     = view.findViewById(R.id.tvTitle)
        val tvDesc: TextView      = view.findViewById(R.id.tvDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_food, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val dish = items[position]

        holder.tvTitle.text = dish.name
        holder.tvDesc.text  = dish.description

        // загрузка изображения блюда
        Glide.with(holder.itemView)
            .load(dish.imageUri)
            .into(holder.imgFood)

        // иконка категории
        holder.imgTag.setImageResource(dish.category.iconRes)

        // иконка «избранное»— всегда заполнена
        holder.imgFavorite.setImageResource(R.drawable.ic_heart_border)
    }

    override fun getItemCount(): Int = items.size

    /** Полностью обновить список избранного */
    fun updateList(newItems: List<Dishes>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}