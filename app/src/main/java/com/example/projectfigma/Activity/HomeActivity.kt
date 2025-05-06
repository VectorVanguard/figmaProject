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
import com.example.projectfigma.Fragments.FoodCategoryFragment
import com.example.projectfigma.R

class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: BestSellerAdapter
    private lateinit var dao: BestSellerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val dataBase = DataBase.getDb(this)
        dao = dataBase.getBestSellerDao()



        val items = dao.getAll()

        val rv = findViewById<RecyclerView>(R.id.rvBestSellers)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = BestSellerAdapter(items) { item ->
            Toast.makeText(this, "Clicked: ${item.price}", Toast.LENGTH_SHORT).show()
        }
        rv.adapter = adapter

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()
    }

}