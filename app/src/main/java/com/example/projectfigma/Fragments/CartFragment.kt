package com.example.projectfigma.Fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Adapters.CartAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment(R.layout.fragment_basket) {

    private lateinit var emptyView: View
    private lateinit var fullView: View
    private lateinit var rvCart: RecyclerView
    private lateinit var adapter: CartAdapter
    private val cartDao by lazy { DataBase.getDb(requireContext()).getDishesDao() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyView = view.findViewById(R.id.empty_cart_view)
        fullView = view.findViewById(R.id.full_cart_view)
        rvCart = fullView.findViewById(R.id.rv_cart_items)
        rvCart.layoutManager = LinearLayoutManager(requireContext())
        rvCart.adapter = adapter

        loadCart()
    }

    private fun loadCart() {
        lifecycleScope.launch {
            val items = withContext(Dispatchers.IO) {
                cartDao.getAll()
            }
            if (items.isEmpty()) showEmpty()
        }
    }

    private fun showEmpty() {
        emptyView.visibility = View.VISIBLE
        fullView.visibility = View.GONE
    }

    /*
    private fun showFull(items: List<Dishes>) {
        emptyView.visibility = View.GONE
        fullView.visibility = View.VISIBLE

        fullView.findViewById<TextView>(R.id.full_title)
            .text = "You have ${items.sumOf { it.quantity }} items in the cart"

        adapter.submitList(items)
        // обновляем итог:
        val subtotal = items.sumOf { it.price * it.quantity }
        fullView.findViewById<TextView>(R.id.tv_subtotal)
            .text = "Subtotal: \$${"%.2f".format(subtotal)}"

        fullView.findViewById<Button>(R.id.btn_checkout)
            .setOnClickListener {
                // действие при оформлении
            }
    }
    private fun updateQuantity(item: CartItem, delta: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val newQty = (item.quantity + delta).coerceAtLeast(0)
            if (newQty == 0) {
                cartDao.delete(item)
            } else {
                cartDao.update(item.copy(quantity = newQty))
            }
            loadCart() // перезагрузить список и UI
        }
    }
        */
}
