package com.example.plant.activities.Home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.plant.FragmentHomeActivity
import com.example.plant.R
import com.example.plant.TestRvPlantType
import com.example.plant.activities.Login.LoginActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnLogout = findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener() {
            val stillLoginPre = getSharedPreferences("stillLogin", Context.MODE_PRIVATE)
            val editor = stillLoginPre.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        var showallPlantTypeBtn = findViewById<Button>(R.id.btnShowAllPlantType)
            .setOnClickListener {
                startActivity(Intent(this, TestRvPlantType::class.java))
            }

        var homeFragment = findViewById<Button>(R.id.btnHomeFragment)
            .setOnClickListener {
                startActivity(Intent(this, FragmentHomeActivity::class.java))
            }

        }
}
