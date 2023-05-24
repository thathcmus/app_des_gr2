package com.example.plant.activities.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.plant.R
import com.example.plant.activities.UpdateProfile.UpdateProfileActivity
import com.example.plant.constant.constant
import com.example.plant.model.User
import com.example.plant.util.ProgressBarLoading
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.editUser
import kotlinx.android.synthetic.main.activity_profile.ivLocationIcon
import kotlinx.android.synthetic.main.activity_profile.tvLocationUser
import kotlinx.android.synthetic.main.activity_profile.tvNameUser

class ProfileActivity : AppCompatActivity() {
    private val currentUserAuth = Firebase.auth.currentUser
    private val db = Firebase.firestore
    private var currentUser = User()
    private val loadingDialog = ProgressBarLoading(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        getUserDetail()
        listenerEvent()

    }
    fun getUserDetail() {
        loadingDialog.startLoading()
        db.collection("users").document(currentUserAuth!!.uid)
        .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    currentUser = document.toObject(User::class.java)!!
                    tvNameUser.text = currentUser?.fullName
                    tvLocationUser.text = currentUser?.location
                    if (currentUser?.location == "") {
                        ivLocationIcon.isVisible = false
                    }
                    loadingDialog.hideLoading()
                } else {
                    Log.e("get database", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("get database", "get userDetail failed", exception)
            }
    }
    fun listenerEvent(){
        editUser.setOnClickListener(){
            val intent = Intent(this, UpdateProfileActivity::class.java)
            intent.putExtra(constant.USER_DETAIL, currentUser)
            startActivity(intent)
        }
    }
}