package com.example.projectfigma.Activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Adapters.BestSellerAdapter
import com.example.projectfigma.DAO.DishesDao
import com.example.projectfigma.DAO.SessionDao
import com.example.projectfigma.DAO.UserDao
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.User
import com.example.projectfigma.Fragments.*
import com.example.projectfigma.Fragments.Cart.CartFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar
import com.example.projectfigma.Util.SwitchCard

class HomeActivity : AppCompatActivity(),
    HeaderButtonsFragment.Listener {

    private lateinit var adapter: BestSellerAdapter
    private lateinit var dao: DishesDao
    private lateinit var userDao: UserDao
    private lateinit var sessionDao: SessionDao
    private lateinit var drawer: DrawerLayout
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        StatusBar.hideStatusBar(window)

        val db = DataBase.getDb(this)
        dao = db.getDishesDao()
        userDao = db.getUserDao()
        sessionDao = db.getSessionDao()
        user = (sessionDao.getSession()?.user ?: null)!!
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        adapter = BestSellerAdapter(emptyList()) { item ->
            SwitchCard.switchDish(
                item,
                this,
                FoodDetailActivity::class.java
            )
        }
        val rv = findViewById<RecyclerView>(R.id.rvBestSellers).apply {
            layoutManager = LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = this@HomeActivity.adapter
        }

        dao.getBestSellersWithLimit(4).observe(this) { list ->
            adapter.updateList(list)
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()

        val filterButton = findViewById<ImageView>(R.id.filterIcon)
        filterButton.setOnClickListener {
            val intent = Intent(this, FiltersActivity::class.java)
            startActivity(intent)
            finish()
        }
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
            name = user?.name.takeUnless { it.isNullOrBlank() } ?: "Гость",
            email = user?.gmail.takeUnless { it.isNullOrBlank() } ?: "Не указано",
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