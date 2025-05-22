package com.example.projectfigma.Fragments.Cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Adapters.OrderSummaryAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderSummaryFragment : Fragment(R.layout.fragment_order_summary) {

    private val dao by lazy { DataBase.getDb(requireContext()).getProductInCartDAO() }
    private lateinit var adapter: OrderSummaryAdapter

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        val rv = v.findViewById<RecyclerView>(R.id.rvOrderSummary)
        rv.layoutManager = LinearLayoutManager(requireContext())

        // Загрузка данных из БД
        lifecycleScope.launch {
            val items = withContext(Dispatchers.IO) { dao.getAll() }
            adapter = OrderSummaryAdapter(
                items,
                onCancel = { product ->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) { dao.delete(product) }
                        reload()
                    }
                },
                onQuantityChanged = { product, qty ->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            dao.update(product.copy(quantity = qty))
                        }
                        reload()
                    }
                }
            )
            rv.adapter = adapter
        }
    }

    private fun reload() {
        lifecycleScope.launch {
            val fresh = withContext(Dispatchers.IO) { dao.getAll() }
            adapter.updateItems(fresh)

            val subtotal = fresh.sumOf { it.dish.price * it.quantity }
            val total    = subtotal + 5.0 + 3.0

            requireActivity().runOnUiThread {
                requireActivity()
                    .findViewById<TextView>(R.id.tv_subtotal_value)
                    ?.text = "$${"%.2f".format(subtotal)}"

                requireActivity()
                    .findViewById<TextView>(R.id.tv_total_value)
                    ?.text = "$${"%.2f".format(total)}"
            }
        }
    }
}
