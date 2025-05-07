package com.example.projectfigma.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projectfigma.DataBase.DataBase
import com.example.projectfigma.Entites.AppSettings
import com.example.projectfigma.Entites.Session
import com.example.projectfigma.R
import com.example.projectfigma.Util.StatusBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBar.hideStatusBar(window)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val db = DataBase.getDb(applicationContext)
            val sessionDao = db.getSessionDao()

            val session = withContext(Dispatchers.IO) {
                sessionDao.getSession()
            }
            if (session?.isLoggedIn == true) {
                startActivity(
                    Intent(this@MainActivity, HomeActivity::class.java)
                        .putExtra("user_email", session.userEmail)
                )
                finish()
                return@launch
            }

            val settingsDao = db.getSettingsDao()
            val settings = withContext(Dispatchers.IO) {
                settingsDao.getSettings()
                    ?: AppSettings(id = 0, isFirstRun = true).also {
                        settingsDao.upsert(it)
                    }
            }

            val nextScreen = if (settings.isFirstRun) {
                withContext(Dispatchers.IO) {
                    settingsDao.upsert(settings.copy(isFirstRun = false))
                }
                WelcomeActivity::class.java
            } else {
                LogActivity::class.java
            }

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@MainActivity, nextScreen))
                finish()
            }, 2000)
        }
    }
}
