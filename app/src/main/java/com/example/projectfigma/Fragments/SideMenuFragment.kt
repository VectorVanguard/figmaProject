package com.example.projectfigma.Fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.projectfigma.Activity.WelcomeActivity
import com.example.projectfigma.R

class SideMenuFragment : Fragment(R.layout.fragment_side_menu) {

    fun setUserData(name: String, email: String, @DrawableRes avatarRes: Int) {
        view?.apply {
            findViewById<ImageView>(R.id.imgAvatar).setImageResource(avatarRes)
            findViewById<TextView>(R.id.tvUserName).text = name
            findViewById<TextView>(R.id.tvUserEmail).text = email
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupItem(view, R.id.itemOrders,   R.drawable.ic_orders,   "My Orders")        { onOrdersClicked() }
        setupItem(view, R.id.itemProfile,  R.drawable.ic_profile,  "My Profile")       { onProfileClicked() }
        setupItem(view, R.id.itemAddress,  R.drawable.ic_location, "Delivery Address"){ onAddressClicked() }
        setupItem(view, R.id.itemPayments, R.drawable.ic_card,     "Payment Methods") { onPaymentsClicked() }
        setupItem(view, R.id.itemContact,  R.drawable.ic_phone,    "Contact Us")       { onContactClicked() }
        setupItem(view, R.id.itemFaqs,     R.drawable.ic_chat,     "Help & FAQs")      { onFaqsClicked() }
        setupItem(view, R.id.itemSettings, R.drawable.ic_settings, "Settings")         { onSettingsClicked() }
        setupItem(view, R.id.itemLogout,   R.drawable.ic_logout,   "Log Out")          { onLogoutClicked() }
    }

    private fun setupItem(
        root: View,
        @IdRes containerId: Int,
        @DrawableRes iconRes: Int,
        title: String,
        clickAction: () -> Unit
    ) {
        val container = root.findViewById<View>(containerId)
        container.findViewById<ImageView>(R.id.imgIcon).setImageResource(iconRes)
        container.findViewById<TextView>(R.id.tvTitle).text = title
        container.setOnClickListener {
            clickAction()
            closeMenu()
        }
    }

    private fun onOrdersClicked() {
        Toast.makeText(requireContext(), "Заказы нажаты", Toast.LENGTH_SHORT).show()
    }

    private fun onProfileClicked() {
        Toast.makeText(requireContext(), "Профиль открыт", Toast.LENGTH_SHORT).show()
        // TODO: добавить логику перехода в профиль
    }

    private fun onAddressClicked() {
        Toast.makeText(requireContext(), "Адрес доставки открыт", Toast.LENGTH_SHORT).show()
        // TODO: добавить логику перехода к адресам доставки
    }

    private fun onPaymentsClicked() {
        Toast.makeText(requireContext(), "Способы оплаты открыты", Toast.LENGTH_SHORT).show()
        // TODO: добавить логику перехода к методам оплаты
    }

    private fun onContactClicked() {
        Toast.makeText(requireContext(), "Контакты открыты", Toast.LENGTH_SHORT).show()
        // TODO: добавить логику перехода к контактам
    }

    private fun onFaqsClicked() {
        Toast.makeText(requireContext(), "Помощь и FAQ открыты", Toast.LENGTH_SHORT).show()
        // TODO: добавить логику перехода к справке и FAQ
    }

    private fun onSettingsClicked() {
        Toast.makeText(requireContext(), "Настройки открыты", Toast.LENGTH_SHORT).show()
        // TODO: добавить логику перехода в настройки
    }

    private fun onLogoutClicked() {
        Toast.makeText(requireContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
        startActivity(android.content.Intent(requireContext(), WelcomeActivity::class.java))
        requireActivity().finish()
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
}
