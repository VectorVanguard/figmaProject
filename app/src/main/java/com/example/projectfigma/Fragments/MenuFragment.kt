package com.example.projectfigma.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Activity.FoodDetailActivity
import com.example.projectfigma.Adapters.MenuAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.Enums.SortType
import com.example.projectfigma.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuFragment : Fragment(R.layout.activity_food_detail) {

    private lateinit var adapter: MenuAdapter
    private lateinit var db: com.example.projectfigma.DAO.DishesDao
    private var currentSort: SortType = SortType.POPULAR

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MenuAdapter(mutableListOf()) { dish ->
            val intent = Intent(requireContext(), FoodDetailActivity::class.java)
            intent.putExtra("dish_id", dish.id)
            startActivity(intent)
        }

        val rv = view.findViewById<RecyclerView>(R.id.rvMenu)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        db = DataBase.getDb(requireContext()).getDishesDao()

        view.findViewById<TextView>(R.id.tvSort).setOnClickListener {
            toggleSort()
            loadData()
        }

        loadData()
    }

    private fun toggleSort() {
        currentSort = when (currentSort) {
            SortType.POPULAR -> SortType.PRICE
            SortType.PRICE -> SortType.RATING
            SortType.RATING -> SortType.POPULAR
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            val dishes = withContext(Dispatchers.IO) {
                when (currentSort) {
                    SortType.POPULAR -> db.getAllSortedByPopularityеба()
                    SortType.PRICE -> db.getAllSortedByPrice()
                    SortType.RATING -> db.getAllSortedByRating()
                }
            }
            adapter.updateList(dishes)
        }
    }
}
