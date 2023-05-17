package com.example.plant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.plant.R

class Introduction1 : AppCompatActivity() {
    private lateinit var btnCont: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction1)

        btnCont = findViewById(R.id.btnCont)

        btnCont.setOnClickListener {
            startActivity(Intent(this, Introduction2::class.java))
            finish()
        }
    }
}