package com.example.plant.activities.UpdateProfile

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.plant.R
import com.example.plant.constant.constant
import com.example.plant.model.User
import kotlinx.android.synthetic.main.activity_update_profile.etUpdateEmail
import kotlinx.android.synthetic.main.activity_update_profile.etUpdateGender
import kotlinx.android.synthetic.main.activity_update_profile.etUpdateLocation
import kotlinx.android.synthetic.main.activity_update_profile.etUpdateName
import kotlinx.android.synthetic.main.activity_update_profile.etUpdatePhone

class UpdateProfileActivity : AppCompatActivity() {
    private var currentUser = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        supportActionBar?.hide()
        updateFields()
    }
    fun updateFields() {
        currentUser =  intent.getParcelableExtra(constant.USER_DETAIL, User::class.java)!!
        etUpdateName.setText(currentUser?.fullName)
        etUpdateEmail.setText(currentUser?.email)
        etUpdateEmail.isEnabled = false
        if(currentUser.phone != 0L)
        {
            etUpdatePhone.setText(currentUser?.phone.toString())
        }
        etUpdateGender.setText(currentUser?.gender)
        etUpdateLocation.setText(currentUser?.location)
    }

}