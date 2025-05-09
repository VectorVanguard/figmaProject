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

class RecommendAdapter (
    private val items: List<Dishes>
) : RecyclerView.Adapter<RecommendAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivFood: ImageView = itemView.findViewById(R.id.ivFood)
        private val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)

        fun bind(food: Dishes) {
            Glide.with(itemView).load(food.imageUri).into(ivFood)
            tvRating.text = String.format("%.1f", food.rating)
            tvPrice.text = "$${String.format("%.2f", food.price)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}