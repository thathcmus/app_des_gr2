package com.example.plant.activities.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.plant.R
import com.example.plant.activities.UpdateProfile.UpdateProfileActivity
import com.example.plant.constant.constant
import com.example.plant.firestore.Firestore
import com.example.plant.glide.GlideLoader
import com.example.plant.model.User
import kotlinx.android.synthetic.main.activity_profile.editUser
import kotlinx.android.synthetic.main.activity_profile.ivAvatarUser
import kotlinx.android.synthetic.main.activity_profile.ivLocationIcon
import kotlinx.android.synthetic.main.activity_profile.tvLocationUser
import kotlinx.android.synthetic.main.activity_profile.tvNameUser

class ProfileActivity : AppCompatActivity() {
    private var currentUser = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        Firestore().getUserDetail(this)
        listenerEvent()

    }
    fun setCurrentUser(currentUser: User){
        this.currentUser = currentUser
    }
    fun ShowUIInfo(UserInfo : User){
        GlideLoader(this@ProfileActivity).loadUserPictureFromUrl(UserInfo.avatar,ivAvatarUser)
        tvNameUser.text = UserInfo?.fullName
        tvLocationUser.text = UserInfo?.location
        if (UserInfo?.location == "") {
            ivLocationIcon.isVisible = false
        }
    }
    fun listenerEvent(){
        editUser.setOnClickListener(){
            val intent = Intent(this, UpdateProfileActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(constant.USER_DETAIL, currentUser)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}