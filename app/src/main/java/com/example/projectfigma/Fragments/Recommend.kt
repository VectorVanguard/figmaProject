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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Recommend : Fragment(R.layout.fragment_recommend) {
    private val db by lazy { DataBase.getDb(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.rvRecommend)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        viewLifecycleOwner.lifecycleScope.launch {
            // 1. Получаем пользователя в IO
            val sessionEmail = db.getSessionDao().getSession()?.userEmail.orEmpty()
            val user = withContext(Dispatchers.IO) {
                db.getUserDao().getUserByEmail(sessionEmail)
            }

            // 2. Теперь наблюдаем LiveData рекомендаций
            //    и каждый раз создаём новый адаптер с реальным списком
            withContext(Dispatchers.Main) {
                db.getDishesDao()
                    .getRecommend()
                    .observe(viewLifecycleOwner) { list ->
                        val adapter = RecommendAdapter(list)
                        rv.adapter = adapter
                    }
            }
        }
    }
}