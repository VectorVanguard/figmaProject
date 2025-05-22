package com.example.projectfigma.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)

        StatusBar.hideStatusBar(window)

        val image : ImageView = findViewById(R.id.payNow)
        image.setOnClickListener(){
            openActivity(OrderConfirmed::class.java)
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