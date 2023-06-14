package com.example.plant.activities.Home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.plant.AddingNewActivity
import com.example.plant.R
import com.example.plant.constant.constant
import com.example.plant.fragment.HomeFragment
import com.example.plant.fragment.ProfileFragment
import com.example.plant.fragment.UpdateProfileFragment
import com.example.plant.util.FragmentUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_home.bottomNavBar

//import kotlinx.android.synthetic.main.activity_home.btnLogout
//import kotlinx.android.synthetic.main.activity_home.btnProfile

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        init()
        listenerEvent()

        val floatingBtnAdding = findViewById<FloatingActionButton>(R.id.floatingBtnAdding)
            .setOnClickListener {
                startActivity(Intent(this, AddingNewActivity::class.java))
            }

    }
    fun init()
    {
        replaceFragment(HomeFragment())
    }
    fun listenerEvent(){
        bottomNavBar.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home_action ->  {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.profile_action -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }
    fun replaceFragment(mFragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.HomeFrameLayout,mFragment)
        transaction.commit()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       if (resultCode == Activity.RESULT_OK) {
           if (requestCode == constant.PICK_IMAGE_REQUEST_CODE) {
               val uri : Uri = data?.data!!
               val updateProfileFragment = supportFragmentManager.findFragmentById(R.id.HomeFrameLayout)
               if(updateProfileFragment is UpdateProfileFragment){
                   updateProfileFragment?.setAvatarProfile(uri)
               }
           }
       }
    }
}
