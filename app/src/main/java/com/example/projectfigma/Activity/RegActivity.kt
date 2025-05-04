package com.example.projectfigma.Activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.User
import com.example.projectfigma.R
import com.example.projectfigma.Util.Password
import com.example.projectfigma.databinding.ActivityRegBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RegActivity() : AppCompatActivity(){
    private lateinit var passwordEditText: EditText
    private lateinit var binding: ActivityRegBinding
    private var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dateBase = DataBase.getDb(this)

        setupDatePicker()
        Reg(binding, dateBase)

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelActivity())
            .commit()

         passwordEditText = binding.passwordEditText
         Password.setIsVisable(passwordEditText)


        binding.goToLogIn.setOnClickListener{
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.exitArrow.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun Reg(binding: ActivityRegBinding, dataBase: DataBase) {
        binding.buttonSingUp.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()

            Thread {
                val existingUser = dataBase.getUserDao().getUserByEmail(email)
                runOnUiThread {
                    if (existingUser != null) {
                        Toast.makeText(this, "Пользователь с такой почтой уже существует", Toast.LENGTH_SHORT).show()
                    } else {
                        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        val dateString = binding.DateOfBirthEdit.text.toString()
                        val parsedDate: Date? = try {
                            dateFormat.parse(dateString)
                        } catch (e: ParseException) {
                            e.printStackTrace()
                            null
                        }

                        if (parsedDate != null) {
                            val user = User(
                                null,
                                binding.fullNameText.text.toString(),
                                binding.passwordEditText.text.toString(),
                                email,
                                binding.mobileNumberEdit.text.toString(),
                                selectedDate ?: Date()
                            )

                            Thread {
                                dataBase.getUserDao().insertUser(user)
                                runOnUiThread {
                                    Toast.makeText(this, "Пользователь зарегистрирован", Toast.LENGTH_SHORT).show()
                                    // TODO: переход на другой экран
                                }
                            }.start()
                        } else {
                            Toast.makeText(this, "Неверный формат даты", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }.start()
        }
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        binding.DateOfBirthEdit.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDate = calendar.time
                    binding.DateOfBirthEdit.setText(dateFormat.format(selectedDate!!))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }
}