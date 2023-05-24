package com.example.plant.activities.Home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.plant.R
import com.example.plant.activities.Login.LoginActivity
import com.example.plant.activities.Profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_home.btnLogout
import kotlinx.android.synthetic.main.activity_home.btnProfile

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        listenerEvent()
    }
    fun listenerEvent(){
        btnLogout.setOnClickListener() {
            val stillLoginPre = getSharedPreferences("stillLogin", Context.MODE_PRIVATE)
            val editor = stillLoginPre.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnProfile.setOnClickListener() {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
