package com.example.projectfigma.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfigma.R

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiti_help)

        val helpWithOrder = findViewById<LinearLayout>(R.id.help_with_order)
        val helpCenter = findViewById<LinearLayout>(R.id.help_center)

        helpWithOrder.setOnClickListener {
            val intent = Intent(this, OrderHelpActivity::class.java)
            startActivity(intent)
        }

        helpCenter.setOnClickListener {
            val intent = Intent(this, HelpCenterActivity::class.java)
            startActivity(intent)
        }
    }
}
