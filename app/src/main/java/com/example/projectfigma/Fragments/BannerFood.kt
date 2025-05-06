package com.example.projectfigma.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.projectfigma.Adapters.PromoAdapter
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import com.example.projectfigma.databinding.FragmentBannerFoodBinding
import com.example.projectfigma.databinding.FragmentBestSellerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BannerFood : Fragment() {
    private var _binding: FragmentBannerFoodBinding? = null
    private val binding get() = _binding!!
    private lateinit var promoAdapter: PromoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Инициализируем адаптер
        promoAdapter = PromoAdapter(emptyList())
        binding.viewPager.adapter = promoAdapter

        // 2) Настраиваем TabLayoutMediator, сразу задаём иконки
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, _ ->
            tab.setIcon(R.drawable.tab_indicator_unselected)
        }.attach()

        // 3) Подписываемся на смену вкладки, чтобы менять иконки
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.setIcon(R.drawable.tab_indicator_selected)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.setIcon(R.drawable.tab_indicator_unselected)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // 4) Тут же можете запустить автопрокрутку, если она нужна в фрагменте
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (promoAdapter.itemCount > 1) {
                    val next = (binding.viewPager.currentItem + 1) % promoAdapter.itemCount
                    binding.viewPager.setCurrentItem(next, true)
                }
                handler.postDelayed(this, 4000)
            }
        }, 4000)

        // 5) А обновление списка (LiveData) можно тоже повесить здесь:
        val dao = DataBase.getDb(requireContext()).getBestSellerDao()
        dao.getAllV().observe(viewLifecycleOwner) { list ->
            promoAdapter.updateList(list)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBannerFoodBinding.inflate(inflater,  container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}