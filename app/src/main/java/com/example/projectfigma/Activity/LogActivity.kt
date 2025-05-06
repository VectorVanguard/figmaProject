package com.example.projectfigma.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.Session
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.Password
import com.example.projectfigma.Util.StatusBar
import com.example.projectfigma.databinding.ActivityLogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBar.hideStatusBar(window)

        val db = DataBase.getDb(this)
        val userDao = db.getUserDao()
        val sessionDao = db.getSessionDao()

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()

        Password.setIsVisable(binding.passwordEditText)

        binding.goToSignUp.setOnClickListener {
            startActivity(Intent(this, RegActivity::class.java))
            finish()
        }

        binding.forgetPassword.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            if (email.isBlank()) {
                Toast.makeText(this, "Введите email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    userDao.getUserByEmail(email)
                }
                if (user != null) {
                    startActivity(
                        Intent(this@LogActivity, ForgetPasswordActivity::class.java)
                            .putExtra("user_email", email)
                    )
                    finish()
                } else {
                    Toast.makeText(
                        this@LogActivity,
                        "Пользователя с такой почтой не существует",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.exitArrow.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.buttonLogIn.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    userDao.getUserByEmail(email)
                }
                when {
                    user == null -> {
                        Toast.makeText(this@LogActivity, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                    }
                    user.password != password -> {
                        Toast.makeText(this@LogActivity, "Неверный пароль", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        withContext(Dispatchers.IO) {
                            sessionDao.upsert(Session(id = 0, isLoggedIn = true, userEmail = email))
                        }
                        Toast.makeText(this@LogActivity, "Вход успешен", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(this@LogActivity, HomeActivity::class.java)
                                .putExtra("user_email", email)
                        )
                        finish()
                    }
                }
            }
        }
    }
}
