package com.example.projectfigma.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar
import com.example.projectfigma.databinding.ActivityAdvertisingPageBinding
import com.example.projectfigma.databinding.ActivityRecomendationBinding

class RecomendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecomendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBar.hideStatusBar(window)

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()
    }
}