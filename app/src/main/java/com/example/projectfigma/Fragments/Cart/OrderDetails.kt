package com.example.projectfigma.Fragments.Cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Adapters.SummaryAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderDetails : Fragment(R.layout.fragment_order_details) {
    private val dao by lazy { DataBase.getDb(requireContext()).getProductInCartDAO() }
    private lateinit var summaryAdapter: SummaryAdapter

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        val rvItems = v.findViewById<RecyclerView>(R.id.tvOrderItemsSummary)
        LinearLayoutManager(requireContext()).also { rvItems.layoutManager = it }
        summaryAdapter = SummaryAdapter(emptyList())
        rvItems.adapter = summaryAdapter

        loadAndDisplay()
    }

    private fun loadAndDisplay() {
        lifecycleScope.launch {
            val items = withContext(Dispatchers.IO) { dao.getAll() }
            summaryAdapter.updateItems(items)

            val subtotal = items.sumOf { it.dish.price * it.quantity }
            val total    = subtotal + 5.0 + 3.0
            view?.findViewById<TextView>(R.id.tvOrderTotal)
                ?.text = "$${"%.2f".format(total)}"
        }
    }
}
