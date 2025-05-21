package com.example.projectfigma.Fragments.Cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Activity.ConfirmOrderActivity
import com.example.projectfigma.Adapters.CartAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.ProductInCart
import com.example.projectfigma.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FullCartFragment : Fragment(R.layout.fragment_full_cart) {
    companion object {
        private const val ARG_ITEMS = "arg_items"
        fun newInstance(items: List<ProductInCart>): FullCartFragment =
            FullCartFragment().apply {
                arguments = bundleOf(ARG_ITEMS to ArrayList(items))
            }
    }

    private val productInCartDAO by lazy { DataBase.getDb(requireContext()).getProductInCartDAO() }
    private val items = mutableListOf<ProductInCart>()
    private lateinit var recycler: RecyclerView
    private lateinit var countProducts: TextView
    private lateinit var checkout: ImageView
    private lateinit var adapter : CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.rv_cart_items)
        countProducts = view.findViewById(R.id.full_title)
        checkout = view.findViewById(R.id.checkout)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        arguments
            ?.getParcelableArrayList<ProductInCart>(ARG_ITEMS)
            ?.let { items.addAll(it) }

        countProducts.text = String.format("You have ${items.size} items in the cart")

        updateTotals()

        checkout.setOnClickListener(){
            openActivity(ConfirmOrderActivity::class.java)
        }

        adapter = CartAdapter(items) { product, newQty ->
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    productInCartDAO.update(product.copy(quantity = newQty))
                }
                val pos = items.indexOfFirst { it.id == product.id }
                if (pos != -1) {
                    items[pos] = items[pos].copy(quantity = newQty)
                    adapter.notifyItemChanged(pos)
                }
                updateTotals()
            }
        }
        recycler.adapter = adapter
    }

    private fun <T> openActivity(activityClass: Class<T>) {
        val ctx = requireContext()
        val intent = Intent(ctx, activityClass)
        startActivity(intent)
    }

    private fun updateTotals() {
        val subtotal = items.sumOf { it.dish.price * it.quantity }
        view?.findViewById<TextView>(R.id.tv_subtotal_value)
            ?.text = "$${"%.2f".format(subtotal)}"
        val total = subtotal + 5.0 + 3.0
        view?.findViewById<TextView>(R.id.tv_total_value)
            ?.text = "$${"%.2f".format(total)}"
    }

}
