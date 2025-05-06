package com.example.projectfigma.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Adapters.BestSellerAdapter
import com.example.projectfigma.DAO.BestSellerDao
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.BestSeller
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R

class HomeActivity : AppCompatActivity() {

    private lateinit var dao: BestSellerDao
    private lateinit var adapter: BestSellerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 1) Инициализируем DAO
        dao = DataBase.getDb(this).getBestSellerDao()

        // 2) Настраиваем RecyclerView для списка BestSeller
        val rv = findViewById<RecyclerView>(R.id.rvBestSellers).apply {
            layoutManager = LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = BestSellerAdapter(emptyList()) { item: BestSeller ->
                Toast.makeText(
                    this@HomeActivity,
                    "Clicked: ${item.price}",
                    Toast.LENGTH_SHORT
                ).show()
            }.also { this@HomeActivity.adapter = it }
        }

        // 3) Подписываемся на LiveData из БД и обновляем адаптер
        dao.getAllV().observe(this) { list ->
            adapter.updateList(list)
        }

        // 4) Вставляем нижнюю панель
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()
    }
}