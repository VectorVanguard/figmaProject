package com.example.projectfigma.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.text.InputType
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.R
import com.example.projectfigma.Util.Password
import com.example.projectfigma.databinding.ActivityLogBinding
import kotlin.concurrent.Volatile

class LogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataBase = DataBase.getDb(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelActivity())
            .commit()

        Password.setIsVisable(binding.passwordEditText)
        setUpLogIn()

        binding.goToSignUp.setOnClickListener{
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.forgetPassword.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()

            Thread {
                val user = dataBase.getUserDao().getUserByEmail(email)

                runOnUiThread {
                    if (user != null) {
                        val intent = Intent(this, ForgetPasswordActivity::class.java)
                        intent.putExtra("user_email", email)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Пользователя с такой почтой не существует", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
        binding.exitArrow.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpLogIn() {
        val db = DataBase.getDb(this)

        binding.buttonLogIn.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Thread {
                val user = db.getUserDao().getUserByEmail(email)

                runOnUiThread {
                    if (user == null) {
                        Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                    } else if (user.password != password) {
                        Toast.makeText(this, "Неверный пароль", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Вход успешен", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }.start()
        }
    }
}
