package com.example.projectfigma.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Adapters.BestSellerAdapter
import com.example.projectfigma.DAO.BestSellerDao
import com.example.projectfigma.DAO.UserDao
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.BestSeller
import com.example.projectfigma.Entites.User
import com.example.projectfigma.Fragments.*
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar

class HomeActivity : AppCompatActivity(),
    HeaderButtonsFragment.Listener {

    private lateinit var adapter: BestSellerAdapter
    private lateinit var dao: BestSellerDao
    private lateinit var userDao: UserDao
    private lateinit var drawer: DrawerLayout

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        StatusBar.hideStatusBar(window)

        drawer = findViewById(R.id.drawer_layout)

        val db = DataBase.getDb(this)
        dao = db.getBestSellerDao()
        userDao = db.getUserDao()

        val email = intent.getStringExtra("user_email")
        if (!email.isNullOrBlank()) {
            user = userDao.getUserByEmail(email)
        }

        val items: List<BestSeller> = dao.getAll()
        findViewById<RecyclerView>(R.id.rvBestSellers).apply {
            layoutManager = LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = BestSellerAdapter(items) { item ->
                Toast.makeText(
                    this@HomeActivity,
                    "Clicked: ${item.price}",
                    Toast.LENGTH_SHORT
                ).show()
            }.also { this@HomeActivity.adapter = it }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()
    }

    override fun onCartClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.right_drawer_container, CartFragment())
            .commitNow()
        drawer.openDrawer(GravityCompat.END)
    }

    override fun onNotificationsClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.right_drawer_container, NotificationFragment())
            .commitNow()
        drawer.openDrawer(GravityCompat.END)
    }

    override fun onProfileClicked() {
        val profileMenu = SideMenuFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.right_drawer_container, profileMenu)
            .commitNow()

        profileMenu.setUserData(
            name = user.name ?: "John Smith",
            email = user.gmail ?: "loremipsum@email.com",
            avatarRes = R.drawable.ic_profile_placeholder
        )

        drawer.openDrawer(GravityCompat.END)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
}
