package com.example.plant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firstUsingPre = getSharedPreferences("firstUsing", Context.MODE_PRIVATE)
        val hasRunBefore = firstUsingPre.getBoolean("hasRunBefore", false)

        if (!hasRunBefore) {
            val editor = firstUsingPre.edit()
            editor.putBoolean("hasRunBefore", true)
            editor.apply()

            // Mở màn hình introduction
            startActivity(Intent(this, Introduction1::class.java))
            finish()
        } else {
            // Mở màn hình login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

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