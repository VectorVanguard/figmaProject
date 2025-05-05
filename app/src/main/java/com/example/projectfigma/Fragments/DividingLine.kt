package com.example.projectfigma.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectfigma.R
import com.example.projectfigma.databinding.FragmentDividingLineBinding
import com.example.projectfigma.databinding.FragmentFoodCategoryCardBinding

class DividingLine : Fragment() {

    private var _binding: FragmentDividingLineBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDividingLineBinding.inflate(inflater,  container, false)
        return binding.root
    }
}