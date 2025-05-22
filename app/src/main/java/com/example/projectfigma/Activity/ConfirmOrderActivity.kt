package com.example.projectfigma.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.ProductInCart
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.Fragments.Cart.FullCartFragment
import com.example.projectfigma.Fragments.Cart.FullCartFragment.Companion
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar
import com.example.projectfigma.databinding.ActivityBestSellerBinding
import com.example.projectfigma.databinding.ActivityConfirmOrderBinding

class ConfirmOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmOrderBinding
    private lateinit var items : List<ProductInCart>
    private val productInCartDAO by lazy { DataBase.getDb(this).getProductInCartDAO() }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityConfirmOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBar.hideStatusBar(window)

        items = productInCartDAO.getAll()

        updateTotals()

        binding.placeOrder.setOnClickListener(){
            openActivity(PaymentActivity::class.java)
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

    private fun updateTotals() {
        val subtotal = items.sumOf { it.dish.price * it.quantity }
        findViewById<TextView>(R.id.tv_subtotal_value)
            ?.text = "$${"%.2f".format(subtotal)}"
        val total = subtotal + 5.0 + 3.0
        findViewById<TextView>(R.id.tv_total_value)
            ?.text = "$${"%.2f".format(total)}"
    }
}