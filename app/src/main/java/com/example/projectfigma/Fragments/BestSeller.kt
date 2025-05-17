package com.example.projectfigma.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projectfigma.Activity.AdverstsingPageActivity
import com.example.projectfigma.Activity.BestSellerActivity
import com.example.projectfigma.Activity.RegActivity
import com.example.projectfigma.R
import com.example.projectfigma.databinding.FragmentBestSellerBinding
import com.example.projectfigma.databinding.FragmentDividingLineBinding

class BestSeller : Fragment() {

    private var _binding: FragmentBestSellerBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBestSellerBinding.inflate(inflater,  container, false)

        binding?.tvViewAll?.setOnClickListener{
            val intent = Intent(requireContext(), BestSellerActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding!!.root
    }
}