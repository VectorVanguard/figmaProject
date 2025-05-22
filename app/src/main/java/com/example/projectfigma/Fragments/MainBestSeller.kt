package com.example.projectfigma.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Activity.FoodDetailActivity
import com.example.projectfigma.Adapters.MainBestSellerAdapter
import com.example.projectfigma.DAO.DishesDao
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.Dishes
import com.example.projectfigma.R
import com.example.projectfigma.Util.SwitchCard

class MainBestSeller : Fragment(R.layout.fragment_main_best_seller) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainBestSellerAdapter
    private lateinit var database: DataBase
    private lateinit var dishesDao: DishesDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.popularRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        database = DataBase.getDb(requireContext())
        dishesDao = database.getDishesDao()

        dishesDao.getBestSellers().observe(viewLifecycleOwner) { dishList ->
            adapter = MainBestSellerAdapter(dishList,
                switchToSelfPage = { item ->
                    SwitchCard.switchDish(
                        item,
                        requireContext(),
                        FoodDetailActivity::class.java
                    )
                }
            )
            recyclerView.adapter = adapter
        }
    }
}