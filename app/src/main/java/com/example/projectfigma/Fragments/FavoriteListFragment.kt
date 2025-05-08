    package com.example.projectfigma.Fragments

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.lifecycle.lifecycleScope
    import androidx.recyclerview.widget.GridLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.example.projectfigma.Adapters.FavoriteFoodAdapter
    import com.example.projectfigma.DataBase.DataBase
    import com.example.projectfigma.Entites.Dishes
    import com.example.projectfigma.R
    import com.example.projectfigma.databinding.FragmentBestSellerBinding
    import com.example.projectfigma.databinding.FragmentFavoriteListBinding
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext

    class FavoriteListFragment : Fragment(R.layout.fragment_favorite_list) {

        private var _binding: FragmentFavoriteListBinding? = null
        private val binding get() = _binding!!

        private lateinit var adapter: FavoriteFoodAdapter

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            adapter = FavoriteFoodAdapter(mutableListOf())
            binding.rvFoods.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvFoods.adapter = adapter

            lifecycleScope.launch {
                val dishesList = withContext(Dispatchers.IO) {
                    DataBase.getDb(requireContext())
                        .getDishesDao()
                        .getAll()
                }
                adapter.updateList(dishesList)
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }