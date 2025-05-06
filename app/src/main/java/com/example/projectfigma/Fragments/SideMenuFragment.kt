package com.example.projectfigma.Fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.projectfigma.R

class SideMenuFragment : Fragment(R.layout.fragment_side_menu) {

    interface Listener {
        fun onMenuItemSelected(tag: String)
    }

    private var listener: Listener? = null

    fun setUserData(name: String, email: String, @DrawableRes avatarRes: Int) {
        view?.apply {
            findViewById<ImageView>(R.id.imgAvatar).setImageResource(avatarRes)
            findViewById<TextView>(R.id.tvUserName).text = name
            findViewById<TextView>(R.id.tvUserEmail).text = email
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) listener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupItem(view, R.id.itemOrders,   R.drawable.ic_orders,   "My Orders",        "ORDERS")
        setupItem(view, R.id.itemProfile,  R.drawable.ic_profile,  "My Profile",       "PROFILE")
        setupItem(view, R.id.itemAddress,  R.drawable.ic_location, "Delivery Address", "ADDRESS")
        setupItem(view, R.id.itemPayments, R.drawable.ic_card,     "Payment Methods",  "PAYMENTS")
        setupItem(view, R.id.itemContact,  R.drawable.ic_phone,    "Contact Us",       "CONTACT")
        setupItem(view, R.id.itemFaqs,     R.drawable.ic_chat,     "Help & FAQs",      "FAQS")
        setupItem(view, R.id.itemSettings, R.drawable.ic_settings, "Settings",         "SETTINGS")
        setupItem(view, R.id.itemLogout,   R.drawable.ic_logout,   "Log Out",          "LOGOUT")
    }

    private fun setupItem(
        root: View,
        @IdRes containerId: Int,
        @DrawableRes iconRes: Int,
        title: String,
        tag: String
    ) {
        val container = root.findViewById<View>(containerId)
        container.findViewById<ImageView>(R.id.imgIcon).setImageResource(iconRes)
        container.findViewById<TextView>(R.id.tvTitle).text = title
        container.setOnClickListener {
            listener?.onMenuItemSelected(tag)
            closeMenu()
        }
    }

    fun openMenu() {
        requireActivity()
            .findViewById<DrawerLayout>(R.id.drawer_layout)
            .openDrawer(GravityCompat.END)
    }

    fun closeMenu() {
        requireActivity()
            .findViewById<DrawerLayout>(R.id.drawer_layout)
            .closeDrawer(GravityCompat.END)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
