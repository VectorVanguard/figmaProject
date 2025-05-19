package com.example.projectfigma.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.R

class MainBestSellerAdapter (
    private var dishes: List<Dishes>,
    private val switchToSelfPage: (Dishes) -> Unit
) :
    RecyclerView.Adapter<MainBestSellerAdapter.DishViewHolder>() {

    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.dishImage)
        val title: TextView = itemView.findViewById(R.id.dishTitle)
        val description: TextView = itemView.findViewById(R.id.dishDescription)
        val price: TextView = itemView.findViewById(R.id.dishPrice)
        val rating: TextView = itemView.findViewById(R.id.tvRating)
        val category: ImageView = itemView.findViewById(R.id.iconCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish_best_seller, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish  = dishes[position]

        Glide.with(holder.itemView.context)
            .load(Uri.parse(dish.imageUri))
            .into(holder.image)

        holder.category.setImageResource(dish.category.iconRes)
        holder.title.text = dish.name
        holder.description.text = dish.description
        holder.price.text = "$${dish.price}"
        holder.rating.text = "${dish.rating}"

        holder.itemView.setOnClickListener { switchToSelfPage(dish) }
    }

    override fun getItemCount(): Int = dishes.size

    fun updateData(newList: List<Dishes>) {
        dishes = newList
        notifyDataSetChanged()
    }
}