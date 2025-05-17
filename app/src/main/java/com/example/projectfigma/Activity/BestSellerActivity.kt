package com.example.projectfigma.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar
import com.example.projectfigma.databinding.ActivityAdvertisingPageBinding
import com.example.projectfigma.databinding.ActivityBestSellerBinding

class BestSellerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBestSellerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBestSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBar.hideStatusBar(window)

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()
    }
}