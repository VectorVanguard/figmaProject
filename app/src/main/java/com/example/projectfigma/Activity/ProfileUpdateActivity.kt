package com.example.projectfigma.Activity

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfigma.DAO.UserDao
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.User
import com.example.projectfigma.databinding.ActivityProfileUpdateBinding
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import android.app.DatePickerDialog
import android.widget.Toast
import com.example.projectfigma.DAO.SessionDao
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileUpdateBinding
    private lateinit var userDao: UserDao
    private lateinit var sessionDao: SessionDao
    private lateinit var user: User
    private val dateFormatter = SimpleDateFormat("dd / MM / yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DataBase.getDb(this)

        userDao = db.getUserDao()
        sessionDao = db.getSessionDao()

        user = sessionDao.getSession()?.user!!

        // Загрузка данных пользователя
        loadUserData()

        // Настройка DatePicker для даты рождения
        setupDatePicker()

        // Обработчик кнопки обновления
        binding.updateProfileButton.setOnClickListener {
            updateProfile()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.bottomPanel, BottomPanelFragment())
            .commit()
    }

    private fun loadUserData() {
        lifecycleScope.launch {
            with(binding) {
                fullNameValue.setText(user.name)
                dobValue.setText(dateFormatter.format(user.dateOfBirth))
                emailValue.setText(user.gmail)
                phoneValue.setText(user.mobileNumber)
            }
        }
    }

    private fun setupDatePicker() {
        binding.dobValue.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, day)
                    binding.dobValue.setText(dateFormatter.format(selectedDate.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateProfile() {
        val name = binding.fullNameValue.text.toString().trim()
        val email = binding.emailValue.text.toString().trim()
        val phone = binding.phoneValue.text.toString().trim()
        val dobText = binding.dobValue.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || dobText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val dob = dateFormatter.parse(dobText) ?: Date()

            lifecycleScope.launch(Dispatchers.IO) {
                user.name = name
                user.gmail = email
                user.mobileNumber = phone
                user.dateOfBirth = dob

                userDao.updateUser(user)
                val session = sessionDao.getSession()
                session?.user = user
                sessionDao.updateSession(session!!)

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ProfileUpdateActivity,
                        "Profile updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error updating profile: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}