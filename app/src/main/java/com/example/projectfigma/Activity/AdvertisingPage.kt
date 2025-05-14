package com.example.projectfigma.Activity

import android.graphics.Paint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectfigma.R

class AdvertisingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_advertising_page)
        val tv: TextView = findViewById(R.id.customtext1piska)
        tv.paintFlags = tv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

    }
}