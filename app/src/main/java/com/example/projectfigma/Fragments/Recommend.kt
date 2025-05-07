package com.example.projectfigma.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Adapters.RecommendAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import kotlinx.coroutines.launch

//class Recommend : Fragment(R.layout.fragment_recommend) {
//
//    private lateinit var adapter: RecommendAdapter
//    private val db by lazy { DataBase.getInstance(requireContext()) }
//    // допустим, в UserDao есть suspend fun getCurrent(): User
//    // в FoodDao — suspend fun getRecommendedForUser(userId: Int): List<Food>
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val rv = view.findViewById<RecyclerView>(R.id.rvRecommend)
//        rv.layoutManager = GridLayoutManager(requireContext(), 2)
//
//        // загрузка из БД через корутины
//        viewLifecycleOwner.lifecycleScope.launch {
//            val user = db.
//            val recommendList = db.foodDao().getRecommendedForUser(user.id)
//            adapter = RecommendAdapter(recommendList)
//            rv.adapter = adapter
//        }
//    }
//}