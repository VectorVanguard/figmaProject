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

class MenuAdapter(
    private val items: MutableList<Dishes>,
    private val onItemClick: (Dishes) -> Unit
) : RecyclerView.Adapter<MenuAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val imgDish: ImageView = view.findViewById(R.id.imgDish)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvDesc: TextView = view.findViewById(R.id.tvDesc)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvTag: TextView = view.findViewById(R.id.tvTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val dish = items[pos]
        h.tvName.text = dish.name
        h.tvDesc.text = dish.description
        h.tvRating.text = String.format("%.1f", dish.rating)
        h.tvPrice.text = "$%.2f".format(dish.price)
        h.tvTag.text = "${dish.category.length * 2} × ${pos + 1}" // если category — строка

        Glide.with(h.itemView.context)
            .load(dish.imageUri)
            .into(h.imgDish)

        h.itemView.setOnClickListener { onItemClick(dish) }
    }

    override fun getItemCount() = items.size

    fun updateList(newList: List<Dishes>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}
