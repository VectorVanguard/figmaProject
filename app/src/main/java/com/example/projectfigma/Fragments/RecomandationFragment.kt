package com.example.projectfigma.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Activity.FoodDetailActivity
import com.example.projectfigma.Adapters.MainBestSellerAdapter
import com.example.projectfigma.Adapters.MainRecommendAdapter
import com.example.projectfigma.DAO.DishesDao
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import com.example.projectfigma.Util.SwitchCard

class RecomandationFragment : Fragment(R.layout.fragment_recomandation_fragemnt) {

    private lateinit var dataBase: DataBase
    private lateinit var dishesDao: DishesDao

    private val adapter by lazy { MainRecommendAdapter(emptyList(),
            switchToSelfPage = { item ->
                SwitchCard.switchDish(
                    item,
                    requireContext(),
                    FoodDetailActivity::class.java
                )
            }
        ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.popularRecyclerView)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
        rv.adapter = adapter   // устанавливаем адаптер сразу

        dataBase = DataBase.getDb(requireContext())
        dishesDao = dataBase.getDishesDao()

        // Наблюдаем за списком рекомендаций
        dishesDao.getRecommend().observe(viewLifecycleOwner) { dishList ->

            adapter.updateData(dishList)
            adapter.notifyDataSetChanged()
        }
    }
}
