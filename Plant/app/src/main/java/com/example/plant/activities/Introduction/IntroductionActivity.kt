package com.example.plant.activities.Introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.plant.activities.Login.LoginActivity
import com.example.plant.R
import com.example.plant.adapter.IntroViewPagerAdapter
import kotlinx.android.synthetic.main.activity_introduction.btnNext
import kotlinx.android.synthetic.main.activity_introduction.vgIntro
import me.relex.circleindicator.CircleIndicator3

class IntroductionActivity : AppCompatActivity() {
    private var imageList = mutableListOf<Int>()
    private var headerList = mutableListOf<String>()
    private var contentList = mutableListOf<String>()

    private lateinit var btnCont: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        //hide action bar
        supportActionBar?.hide()
        // Intro UI
        createIntro()
        // listen event
        btnNext.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
    private fun addIntro(image: Int, header: String, content: String) {
        imageList.add(image)
        headerList.add(header)
        contentList.add(content)
    }
    private fun createIntro() {
            addIntro(R.drawable.intro1, "Identify Plants", "You can identify the plants you don't know through your camera")
            addIntro(R.drawable.intro2, "Learn Many Plants Species", "Let's learn about the many plant species that exist in this world")
            addIntro(R.drawable.intro3, "Read Many Articles About Plant", "Let's learn more about beautiful plants and read many articles about plants and gardening")
            vgIntro.adapter = IntroViewPagerAdapter(imageList, headerList, contentList)
            vgIntro.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            var indicator : CircleIndicator3 = findViewById<CircleIndicator3>(R.id.ciSwipe)
            indicator.setViewPager(vgIntro)
    }
}