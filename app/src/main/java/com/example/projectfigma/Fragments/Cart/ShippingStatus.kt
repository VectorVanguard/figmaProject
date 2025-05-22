package com.example.projectfigma.Fragments.Cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Activity.ConfirmOrderActivity
import com.example.projectfigma.Activity.HomeActivity
import com.example.projectfigma.Adapters.SummaryAdapter
import com.example.projectfigma.R

class ShippingStatus : Fragment(R.layout.fragment_shipping_status) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        val a : Button = v.findViewById(R.id.btnReturnHome)
        a.setOnClickListener(){
            openActivity(HomeActivity::class.java)
        }
    }

    private fun <T> openActivity(activityClass: Class<T>) {
        val ctx = requireContext()
        val intent = Intent(ctx, activityClass)
        startActivity(intent)
    }

}