package com.example.projectfigma.Adapters

import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.R
import java.util.Date
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class CartAdapter(
    private val onQuantityChange: (Dishes, Int) -> Unit
) : ListAdapter<Dishes, CartAdapter.CartViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImage: ImageView = itemView.findViewById(R.id.iv_cart_image)
        private val tvName: TextView = itemView.findViewById(R.id.tv_cart_name)
        private val tvDateTime: TextView = itemView.findViewById(R.id.tv_cart_datetime)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_cart_price)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tv_cart_quantity)
        private val btnMinus: ImageButton = itemView.findViewById(R.id.btn_cart_minus)
        private val btnPlus: ImageButton = itemView.findViewById(R.id.btn_cart_plus)

        fun bind(item: Dishes) {
            Glide.with(itemView)
                .load(item.imageUri)
                .into(ivImage)

            tvName.text = item.name
            tvDateTime.text = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
                .format(Date())
            tvPrice.text = "$${"%.2f".format(item.price)}"
            tvQuantity.text = "1"

            btnMinus.setOnClickListener {
                onQuantityChange(item, -1)
            }
            btnPlus.setOnClickListener {
                onQuantityChange(item, +1)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Dishes>() {
        override fun areItemsTheSame(oldItem: Dishes, newItem: Dishes): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Dishes, newItem: Dishes): Boolean =
            oldItem == newItem
    }
}
