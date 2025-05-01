package com.example.project.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project.Database.AppDatabase
import com.example.project.R
import com.example.project.Repositories.UserRepository
import kotlinx.coroutines.launch

class LogActivity : AppCompatActivity(){

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var repository: UserRepository

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelActivity())
            .commit()

        var isPasswordVisible = false

        passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        emailEditText = findViewById(R.id.emailEditText)
        loginButton = findViewById(R.id.button_Log_In)

        passwordEditText.setOnTouchListener { _, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (passwordEditText.right - passwordEditText.compoundDrawables[DRAWABLE_END].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    if (isPasswordVisible) {
                        passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    } else {
                        passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    }
                    passwordEditText.setSelection(passwordEditText.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }

        val db = AppDatabase.getDatabase(this)
        repository = UserRepository(db.userDao())

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val isAuthenticated = repository.authenticate(email, password)
                if (isAuthenticated) {
                    runOnUiThread {
                        Toast.makeText(this@LogActivity, "Login successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LogActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@LogActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

}