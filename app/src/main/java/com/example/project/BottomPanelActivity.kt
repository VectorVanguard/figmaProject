package com.example.project
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment


class BottomPanelActivity : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Надуваем макет фрагмента
        return inflater.inflate(R.layout.activity_bottom_panel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем ссылки на элементы
        val navHome = view.findViewById<ImageView>(R.id.nav_home)
        val navFood = view.findViewById<ImageView>(R.id.nav_food)
        val navFav = view.findViewById<ImageView>(R.id.nav_fav)
        val navList = view.findViewById<ImageView>(R.id.nav_list)
        val navSupport = view.findViewById<ImageView>(R.id.nav_support)

        // Устанавливаем обработчики кликов
        /*
        navHome.setOnClickListener { switchFragment(HomeFragment()) }
        navFood.setOnClickListener { switchFragment(FoodFragment()) }
        navFav.setOnClickListener { switchFragment(FavoritesFragment()) }
        navList.setOnClickListener { switchFragment(OrdersFragment()) }
        navSupport.setOnClickListener { switchFragment(SupportFragment()) }
         */
    }
}