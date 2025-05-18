package com.example.projectfigma.Adapters

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.R

class MainRecommendAdapter(
    private var dishes: List<Dishes>
) : RecyclerView.Adapter<MainRecommendAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgFood: ImageView = view.findViewById(R.id.imgFood)
        val imgCategory: ImageView = view.findViewById(R.id.imgCategory)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDesc: TextView = view.findViewById(R.id.tvDesc)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main_recomend, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes[position]

        Glide.with(holder.itemView.context)
            .load(Uri.parse(dish.imageUri))
            .into(holder.imgFood)
        holder.imgCategory.setImageResource(dish.category.iconRes)
        holder.tvTitle.text = dish.name
        holder.tvDesc.text = dish.description
        holder.tvPrice.text = "$" + dish.price.toString()
        holder.tvRating.text = dish.rating.toString()
    }

    override fun getItemCount(): Int = dishes.size
    fun updateData(newItems: List<Dishes>) {
        dishes = newItems
    }
}
