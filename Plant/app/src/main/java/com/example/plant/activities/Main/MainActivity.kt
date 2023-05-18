package com.example.plant.activities.Main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plant.activities.Home.HomeActivity
import com.example.plant.activities.Introduction.IntroductionActivity
import com.example.plant.activities.Login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkStartIntro()
    }
    private fun checkStartIntro() {
        val firstUsingPre = getSharedPreferences("firstUsing", Context.MODE_PRIVATE)
        val hasRunBefore = firstUsingPre.getBoolean("hasRunBefore", false)

        if (!hasRunBefore) {
            val editor = firstUsingPre.edit()
            editor.putBoolean("hasRunBefore", true)
            editor.apply()

            // Mở màn hình introduction
            startActivity(Intent(this, IntroductionActivity::class.java))
            finish()
        } else {
            // Check isLoggedIn or no
            checkLogin()
        }
    }
    private fun checkLogin () {
        val stillLoginPre = getSharedPreferences("stillLogin", Context.MODE_PRIVATE)
        val isLoggedIn = stillLoginPre.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}