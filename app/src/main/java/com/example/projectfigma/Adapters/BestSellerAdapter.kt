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

class BestSellerAdapter(
    private var items: List<Dishes>,
    private val onClick: (Dishes) -> Unit
) : RecyclerView.Adapter<BestSellerAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val ivFood: ImageView = view.findViewById(R.id.ivFood)
        private val tvPrice: TextView = view.findViewById(R.id.tvPrice)

        fun bind(item: Dishes) {
            tvPrice.text = String.format("$%.2f", item.price)
            Glide.with(ivFood.context)
                .load(item.imageUri)
                .centerCrop()
                .into(ivFood)

            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_best_seller, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    fun updateList(newItems: List<Dishes>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}
