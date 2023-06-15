package com.example.plant.activities.Home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.plant.R
import com.example.plant.constant.constant
import com.example.plant.fragment.HomeFragment
import com.example.plant.fragment.ProfileFragment
import com.example.plant.fragment.UpdateProfileFragment
import com.example.plant.util.FragmentUtil
import kotlinx.android.synthetic.main.activity_home.bottomNavBar

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        init()
        listenerEvent()
    }
    fun init()
    {
        FragmentUtil(this).replaceFragment(HomeFragment(),R.id.HomeFrameLayout,true)
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
