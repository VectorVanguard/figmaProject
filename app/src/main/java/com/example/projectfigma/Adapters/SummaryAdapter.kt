package com.example.projectfigma.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Entites.ProductInCart
import com.example.projectfigma.R

class SummaryAdapter(
    private var items: List<ProductInCart>
) : RecyclerView.Adapter<SummaryAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvName  : TextView = view.findViewById(R.id.tvLineName)
        val tvCount : TextView = view.findViewById(R.id.tvLineCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_summary_line, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val prod = items[pos]
        holder.tvName.text  = prod.dish.name
        holder.tvCount.text = "${prod.quantity} items"
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ProductInCart>) {
        items = newItems
        notifyDataSetChanged()
    }
}
