    package com.example.projectfigma.Fragments

    import android.content.Context
    import android.os.Build
    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.annotation.RequiresApi
    import androidx.lifecycle.lifecycleScope
    import androidx.recyclerview.widget.GridLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.example.projectfigma.Adapters.FavoriteFoodAdapter
    import com.example.projectfigma.DataBase.DataBase
    import com.example.projectfigma.Entites.Dishes
    import com.example.projectfigma.Entites.User
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
        private var currentUser: User? = null
        private lateinit var dataBase: DataBase

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onAttach(context: Context) {
            super.onAttach(context)
            dataBase = DataBase.getDb(context)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            // 1) Загрузить текущего пользователя из БД
            lifecycleScope.launch(Dispatchers.IO) {
                currentUser = dataBase
                    .getUserDao().getUserByEmail(
                        dataBase.getSessionDao().getSession()?.userEmail.toString()
                    )
                withContext(Dispatchers.Main) {
                    setupRecycler()
                    loadFavorites()
                }
            }
        }
        private fun setupRecycler() {
            adapter = FavoriteFoodAdapter(mutableListOf()) { dish ->
                currentUser?.let { user ->
                    // 2) Удаляем ID блюда из списка
                    val newList = user.favoriteDishesId.toMutableList()
                    newList.remove(dish.id.toInt())
                    user.favoriteDishesId = newList
                    // 3) Сохраняем пользователя в БД и обновляем UI
                    lifecycleScope.launch(Dispatchers.IO) {
                        DataBase.getDb(requireContext()).getUserDao().updateUser(user)
                        val all = DataBase.getDb(requireContext()).getDishesDao().getAll()
                        val fav = all.filter { it.id.toInt() in newList }
                        withContext(Dispatchers.Main) { adapter.updateList(fav) }
                    }
                }
            }
            binding.rvFoods.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvFoods.adapter = adapter
        }

        private fun loadFavorites() {
            currentUser?.let { user ->
                lifecycleScope.launch(Dispatchers.IO) {
                    val all =  dataBase.getDishesDao().getDishesByIds(user.favoriteDishesId)
                    println(" FFFFFFFFFFFFFFFF " + all.toString())
                    println(" FFFFFFFFFFFFFFFF " + user)
                    withContext(Dispatchers.Main) { adapter.updateList(all) }
                }
            }
        }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }