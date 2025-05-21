package com.example.projectfigma.Adapters

import android.media.Image
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Entites.ProductInCart
import com.example.projectfigma.R
import java.time.format.DateTimeFormatter
import android.view.LayoutInflater
import com.bumptech.glide.Glide

class OrderSummaryAdapter(
    private var items: List<ProductInCart>,
    private val onCancel: (ProductInCart) -> Unit,
    private val onQuantityChanged: (ProductInCart, Int) -> Unit
) : RecyclerView.Adapter<OrderSummaryAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivDish       : ImageView = view.findViewById(R.id.ivDish)
        val tvName       : TextView = view.findViewById(R.id.tvName)
        val tvPrice      : TextView   = view.findViewById(R.id.tvPrice)
        val tvDateTime   : TextView   = view.findViewById(R.id.tvDateTime)
        val tvItemCount  : TextView   = view.findViewById(R.id.tvItemCount)
        val btnCancel    : ImageView = view.findViewById(R.id.btnMinus)
        val btnMinus      : ImageView =      view.findViewById(R.id.minus)
        val tvQuantity   : TextView   = view.findViewById(R.id.tvQuantity)
        val btnPlus      : ImageView  = view.findViewById(R.id.plus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_summary, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val prod = items[pos]
        val dish = prod.dish

        Glide.with(holder.ivDish).load(dish.imageUri).into(holder.ivDish)
        holder.tvName.text      = dish.name
        holder.tvPrice.text     = "$${"%.2f".format(dish.price * prod.quantity)}"
        holder.tvDateTime.text  = prod.creationTime.format(
            DateTimeFormatter.ofPattern("d MMM, HH:mm a")
        )
        holder.tvItemCount.text = "${prod.quantity} items"
        holder.tvQuantity.text = "${prod.quantity}"

        holder.btnCancel.setOnClickListener { onCancel(prod) }
        holder.btnMinus.setOnClickListener {
            val newQty = (prod.quantity - 1).coerceAtLeast(1)
            onQuantityChanged(prod, newQty)
        }
        holder.btnPlus.setOnClickListener {
            onQuantityChanged(prod, prod.quantity + 1)
        }
    }

    fun updateItems(newItems: List<ProductInCart>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}
