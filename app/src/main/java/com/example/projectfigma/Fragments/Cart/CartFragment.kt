package com.example.projectfigma.Fragments.Cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Adapters.CartAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment(R.layout.fragment_cart) {

    private val productInCartDAO by lazy { DataBase.getDb(requireContext()).getProductInCartDAO() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCartState()
    }

    private fun showCartState() {
        lifecycleScope.launch {
            val items = withContext(Dispatchers.IO) {
                productInCartDAO.getAll()
            }
            val fragment = if (items.isEmpty()) {
                EmptyCartFragment()
            } else {
                FullCartFragment.newInstance(items)
            }
            childFragmentManager
                .beginTransaction()
                .replace(R.id.carts, fragment)
                .commit()
        }
    }
}