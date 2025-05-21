package com.example.projectfigma.Fragments
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.projectfigma.Activity.FavoritesActivity
import com.example.projectfigma.Activity.HelpActivity
import com.example.projectfigma.Activity.FoodDetailActivity
import com.example.projectfigma.Activity.HomeActivity
import com.example.projectfigma.R

class BottomPanelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_bottom_panel, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHome     = view.findViewById<ImageView>(R.id.nav_home)
        val navFood     = view.findViewById<ImageView>(R.id.nav_food)
        val navFav      = view.findViewById<ImageView>(R.id.nav_fav)
        val navList     = view.findViewById<ImageView>(R.id.nav_list)
        val navSupport  = view.findViewById<ImageView>(R.id.nav_support)

        navHome    .setOnClickListener { openActivity(HomeActivity::class.java) }
        navSupport .setOnClickListener { openActivity(HelpActivity::class.java) }
        navFav     .setOnClickListener { openActivity(FavoritesActivity::class.java) }
        navFood    .setOnClickListener { openActivity(FoodDetailActivity::class.java) }
        /**
        navList    .setOnClickListener { openActivity(OrdersActivity::class.java) }
         **/
    }

    private fun <T> openActivity(activityClass: Class<T>) {
        val ctx = requireContext()
        val intent = Intent(ctx, activityClass)
        startActivity(intent)
    }
}