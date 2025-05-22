package com.example.projectfigma.Activity

import com.example.projectfigma.DataBase.DataBase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfigma.Repositories.AddressRepository
import com.example.projectfigma.databinding.ActivityAddAddressBinding
import com.example.projectfigma.DAO.UserDao
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.projectfigma.Entites.User
import kotlinx.coroutines.launch

class AddAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAddressBinding
    private lateinit var addressRepository: AddressRepository

    private lateinit var userDao: UserDao
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация репозитория
        val db = DataBase.getDb(this)
        addressRepository = AddressRepository(db.getAddressDao())
        userDao = db.getUserDao()

        val email = intent.getStringExtra("user_email")
        if (!email.isNullOrBlank()) {
            user = userDao.getUserByEmail(email)
        }

        setupUI()
    }

    private fun setupUI() {
        binding.exitArrow.setOnClickListener {
            finish()
        }

        binding.saveButton.setOnClickListener {
            saveAddress()
        }
    }

    private fun saveAddress() {
        val name = binding.nameEditText.text.toString().trim()
        val address = binding.addressEditText.text.toString().trim()
        val isDefault = binding.defaultCheckbox.isChecked

        if (name.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {

            val userId: Int = (user?.id)!!

            try {
                addressRepository.addAddress(userId, name, address, isDefault)
                Toast.makeText(this@AddAddressActivity, "Address saved", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@AddAddressActivity, "Error saving address", Toast.LENGTH_SHORT).show()
            }
        }
    }
}