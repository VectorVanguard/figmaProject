package com.example.projectfigma.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.projectfigma.Activity.AdverstsingPageActivity
import com.example.projectfigma.Activity.FoodDetailActivity
import com.example.projectfigma.Activity.LogActivity
import com.example.projectfigma.Adapters.PromoAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import com.example.projectfigma.Util.SwitchCard
import com.example.projectfigma.databinding.FragmentBannerFoodBinding
import com.example.projectfigma.databinding.FragmentBestSellerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BannerFood : Fragment() {
    private var _binding: FragmentBannerFoodBinding? = null
    private val binding get() = _binding!!
    private lateinit var promoAdapter: PromoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBannerFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        promoAdapter = PromoAdapter(emptyList(),
            onBannerClick = {
                val intent = Intent(requireContext(), AdverstsingPageActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        )
        binding.viewPager.adapter = promoAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, _ ->
            tab.setIcon(com.example.projectfigma.R.drawable.tab_indicator_unselected)
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.setIcon(com.example.projectfigma.R.drawable.tab_indicator_selected)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.setIcon(com.example.projectfigma.R.drawable.tab_indicator_unselected)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            while (isActive) {
                delay(4000)
                if (promoAdapter.itemCount > 1) {
                    val next = (binding.viewPager.currentItem + 1) % promoAdapter.itemCount
                    binding.viewPager.setCurrentItem(next, true)
                }
            }
        }

        val dao = DataBase.getDb(requireContext()).getDishesDao()
        dao.getBestSellers().observe(viewLifecycleOwner) { list ->
            promoAdapter.updateList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}