package com.example.projectfigma.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Activity.BestSellerActivity
import com.example.projectfigma.Activity.FoodDetailActivity
import com.example.projectfigma.Activity.RecomendationActivity
import com.example.projectfigma.Adapters.RecommendAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import com.example.projectfigma.Util.SwitchCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Recommend : Fragment(R.layout.fragment_recommend) {

    private val db by lazy { DataBase.getDb(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.rvRecommend)
        val textRecomend = view.findViewById<TextView>(R.id.tvHeader)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        textRecomend.setOnClickListener(){
            val intent = Intent(requireContext(), RecomendationActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val sessionEmail = db.getSessionDao().getSession()?.userEmail.orEmpty()
            val user = withContext(Dispatchers.IO) {
                db.getUserDao().getUserByEmail(sessionEmail)
            }

            withContext(Dispatchers.Main) {
                db.getDishesDao()
                    .getRecommend()
                    .observe(viewLifecycleOwner) { list ->
                        val adapter = RecommendAdapter(list,
                            switchToSelfPage = { item ->
                                SwitchCard.switchDish(
                                    item,
                                    requireContext(),
                                    FoodDetailActivity::class.java
                                )
                            }
                        )
                        rv.adapter = adapter
                    }
            }
        }
    }
}