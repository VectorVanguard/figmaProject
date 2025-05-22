package com.example.projectfigma.Adapters

import android.os.Build
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.R
import java.util.Date
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectfigma.Entites.ProductInCart
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class CartAdapter(
    private val items: List<ProductInCart>,
    private val onQuantityChanged: (product: ProductInCart, newQty: Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val ivDish       : ImageView  = item.findViewById(R.id.ivDish)
        val tvName       : TextView   = item.findViewById(R.id.tvName)
        val tvPrice      : TextView   = item.findViewById(R.id.tvPrice)
        val tvDateTime   : TextView   = item.findViewById(R.id.tvDateTime)
        val tvQuantity   : TextView   = item.findViewById(R.id.tvQuantity)
        val btnMinus     : ImageView= item.findViewById(R.id.btnMinus)
        val btnPlus      : ImageView= item.findViewById(R.id.btnPlus)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val date = DateTimeFormatter.ofPattern("dd/MM/yy")
        @RequiresApi(Build.VERSION_CODES.O)
        private val time = DateTimeFormatter.ofPattern("HH:mm")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = items[position]
        val dish = product.dish

         Glide.with(holder.ivDish).load(dish.imageUri).into(holder.ivDish)

        holder.tvName.text     = dish.name
        holder.tvPrice.text    = "$${"%.2f".format(dish.price)}"

        val okak = product.creationTime.format(date)
        val kako = product.creationTime.format(time)

        holder.tvDateTime.text = "$okak\n   $kako"
        holder.tvQuantity.text = product.quantity.toString()

        holder.btnMinus.setOnClickListener {
            val newQty = (product.quantity - 1).coerceAtLeast(1)
            onQuantityChanged(product, newQty)
        }
        holder.btnPlus.setOnClickListener {
            val newQty = product.quantity + 1
            onQuantityChanged(product, newQty)
        }
    }

    override fun getItemCount(): Int = items.size
}

