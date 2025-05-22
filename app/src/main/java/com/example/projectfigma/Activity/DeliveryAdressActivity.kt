package com.example.projectfigma.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfigma.databinding.ActivityDeliveryAddressBinding
import com.example.projectfigma.Repositories.AddressRepository
import com.example.projectfigma.Adapters.AddressAdapter
import com.example.projectfigma.DataBase.DataBase
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.projectfigma.Entites.Address
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RadioButton
import com.example.projectfigma.DAO.UserDao
import com.example.projectfigma.Entites.User
import com.example.projectfigma.R

class DeliveryAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryAddressBinding
    private lateinit var addressRepository: AddressRepository
    private lateinit var addressAdapter: AddressAdapter

    private lateinit var userDao: UserDao
    private var user: User? = null
    var currentUserId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация репозитория
        val db = DataBase.getDb(this)
        addressRepository = AddressRepository(db.getAddressDao())

        userDao = db.getUserDao()

        val email = intent.getStringExtra("user_email")
        if (!email.isNullOrBlank()) {
            user = userDao.getUserByEmail(email)
        }

        currentUserId = user?.id!!

        // Настройка кнопки "Добавить адрес"
        binding.addAddressButton.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }

        // Кнопка назад
        binding.exitArrow.setOnClickListener {
            finish()
        }

        // Загрузка адресов
        loadAddresses()
    }

    override fun onResume() {
        super.onResume()
        // Обновляем список при возвращении из AddAddressActivity
        loadAddresses()
    }

    private fun loadAddresses() {
        lifecycleScope.launch {
            val addresses = addressRepository.getAddresses(currentUserId)
            if (addresses.isNotEmpty()) {
                displayAddresses(addresses)
            } else {
                // Показать пустое состояние, если адресов нет
            }
        }
    }

    private fun displayAddresses(addresses: List<Address>) {
        binding.addressContainer.removeAllViews()

        addresses.forEach { address ->
            val view = LayoutInflater.from(this).inflate(R.layout.item_address, binding.addressContainer, false)

            val icon = view.findViewById<ImageView>(R.id.addressIcon)
            val radioButton = view.findViewById<RadioButton>(R.id.addressRadio)

            // Установка иконки в зависимости от названия адреса
            icon.setImageResource(when {
                address.name.contains("дом", ignoreCase = true) -> R.drawable.ic_home
                else -> R.drawable.ic_location
            })

            radioButton.text = "${address.name}\n${address.address}"
            radioButton.isChecked = address.isDefault

            radioButton.setOnClickListener {
                lifecycleScope.launch {
                    addressRepository.setDefaultAddress(address.id ?: return@launch, currentUserId)
                    // Обновляем UI после изменения
                    loadAddresses()
                }
            }

            binding.addressContainer.addView(view)
        }
    }
}