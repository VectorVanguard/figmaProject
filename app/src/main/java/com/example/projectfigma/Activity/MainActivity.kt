package com.example.projectfigma.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBar.hideStatusBar(window)
        setContentView(R.layout.activity_main)
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000) // 2 секунды
        }
}