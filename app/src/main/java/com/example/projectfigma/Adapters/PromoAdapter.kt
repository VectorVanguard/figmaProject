package com.example.projectfigma.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.R

class PromoAdapter(
    private var items: List<Dishes>,
    private val onBannerClick: () -> Unit
) :
    RecyclerView.Adapter<PromoAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.promo_image)
        private val title: TextView = view.findViewById(R.id.title)
        private val discount: TextView = view.findViewById(R.id.discount)
        private val fullCard: CardView = view.findViewById(R.id.fullCard)

        fun bind(item: Dishes) {
            title.text = "Experience our delicious new dish"
            discount.text = "${30}% OFF"
            fullCard.setOnClickListener { onBannerClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    /** Вызываем из Activity/Fragment при получении новых данных */
    fun updateList(newItems: List<Dishes>) {
        items = newItems
        notifyDataSetChanged()
    }
}
