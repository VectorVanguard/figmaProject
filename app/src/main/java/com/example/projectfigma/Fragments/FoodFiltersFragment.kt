package com.example.projectfigma.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectfigma.R
import com.example.projectfigma.databinding.FragmentFoodCategoryCardBinding

class FoodFiltersFragment : Fragment() {

    private var _binding: FragmentFoodCategoryCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodCategoryCardBinding.inflate(inflater,  container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.snacks.setOnClickListener {

            // TODO: показать снэки
        }
        binding.meal.setOnClickListener {
            // TODO: показать основные блюда
        }
        binding.vegan.setOnClickListener {
            // TODO: показать веган-меню
        }
        binding.Dessert.setOnClickListener {
            // TODO: показать десерты
        }
        binding.drinks.setOnClickListener {
            // TODO: показать напитки
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}