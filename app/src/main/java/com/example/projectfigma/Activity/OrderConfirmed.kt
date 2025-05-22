package com.example.projectfigma.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar

class OrderConfirmed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_confirmed)

        StatusBar.hideStatusBar(window)

        val tvTrackOrder : TextView = findViewById(R.id.tvTrackOrder)
        tvTrackOrder.setOnClickListener(){
            openActivity(DeliveryTime::class.java)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()
    }

    private fun <T> openActivity(activityClass: Class<T>) {
        val ctx = this
        val intent = Intent(ctx, activityClass)
        startActivity(intent)
    }
}