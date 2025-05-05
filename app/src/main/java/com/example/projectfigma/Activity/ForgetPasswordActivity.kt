package com.example.projectfigma.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Fragments.BottomPanelFragment
import com.example.projectfigma.R
import com.example.projectfigma.Util.Password
import com.example.projectfigma.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dateBase = DataBase.getDb(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonPanel, BottomPanelFragment())
            .commit()

        Password.setIsVisable(binding.passwordEditText)
        Password.setIsVisable(binding.confirmPassword)

        val email : String? = intent.getStringExtra("user_email")

        binding.changePassword.setOnClickListener{
            changePassword(email,dateBase)
        }

        binding.exitArrow.setOnClickListener{
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun changePassword(email: String?, dataBase: DataBase) {
        Thread {
            if (email != null) {
                val user = dataBase.getUserDao().getUserByEmail(email)

                val password = binding.passwordEditText.text.toString()
                val confirm = binding.confirmPassword.text.toString()

                runOnUiThread {
                    if (user == null) {
                        Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                    } else if (password != confirm) {
                        Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                    } else {
                        user.password = password
                        Thread {
                            dataBase.getUserDao().updateUser(user)
                            runOnUiThread {
                                Toast.makeText(this, "Пароль успешно обновлён", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LogActivity::class.java))
                                finish()
                            }
                        }.start()
                    }
                }
            }
        }.start()
    }


}