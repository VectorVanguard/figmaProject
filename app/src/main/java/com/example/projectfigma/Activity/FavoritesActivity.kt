package com.example.projectfigma.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar
import com.example.projectfigma.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBar.hideStatusBar(window)

        val dateBase = DataBase.getDb(this)

        val rv = findViewById<RecyclerView>(R.id.rvFoods).apply {
            layoutManager = GridLayoutManager(context, 2)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()
    }
}