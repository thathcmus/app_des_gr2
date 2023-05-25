package com.example.plant.firestore

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.plant.activities.Profile.ProfileActivity
import com.example.plant.activities.RegisterAccount.RegisterActivity
import com.example.plant.activities.UpdateProfile.UpdateProfileActivity
import com.example.plant.constant.constant
import com.example.plant.model.User
import com.example.plant.util.ProgressBarLoading
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.ivLocationIcon
import kotlinx.android.synthetic.main.activity_profile.tvLocationUser
import kotlinx.android.synthetic.main.activity_profile.tvNameUser

class Firestore {
    private val mFireStore = Firebase.firestore
    fun registerUser(mActivity: RegisterActivity, UserInfo: User)
    {
        mFireStore.collection(constant.USER_COLLECTION).document(UserInfo.id)
            .set(UserInfo)
            .addOnSuccessListener {
                //do something
            }
            .addOnFailureListener {
                Toast.makeText(mActivity, "Store User Information failed!!!", Toast.LENGTH_SHORT).show()
            }
    }
    fun getCurrentUserAuth(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    fun getUserDetail(mActivity: Activity) {
        val loadingDialog = ProgressBarLoading(mActivity)
        loadingDialog.startLoading()
        mFireStore.collection(constant.USER_COLLECTION).document(getCurrentUserAuth()!!.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val currentUser = document.toObject(User::class.java)!!
                    when(mActivity) {
                        is ProfileActivity -> {
                            mActivity.setCurrentUser(currentUser)
                            mActivity.ShowUIInfo(currentUser)
                        }
                    }
                    loadingDialog.hideLoading()
                } else {
                    Log.e("get database", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                loadingDialog.hideLoading()
                Log.e("get database", "get userDetail failed", exception)
            }
    }
    fun updateUserInfo(mActivity: Activity, userHashMap: HashMap<String, Any>){
        val loadingDialog = ProgressBarLoading(mActivity)
        mFireStore.collection(constant.USER_COLLECTION).document(getCurrentUserAuth()!!.uid)
            .update(userHashMap)
            .addOnSuccessListener {
                when(mActivity) {
                    is UpdateProfileActivity -> {
                        mActivity.updateUserSuccessConfirm()
                    }
                }
                loadingDialog.hideLoading()
            }
            .addOnFailureListener{ e ->
                loadingDialog.hideLoading()
                Log.e("update UserInfo", "update UserInfo failed", e)
            }
    }

}