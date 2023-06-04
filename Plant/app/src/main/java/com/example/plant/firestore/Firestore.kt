package com.example.plant.firestore

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.plant.activities.RegisterAccount.RegisterActivity
import com.example.plant.constant.constant
import com.example.plant.fragment.HomeFragment
import com.example.plant.fragment.ProfileFragment
import com.example.plant.fragment.UpdateProfileFragment
import com.example.plant.model.User
import com.example.plant.util.ProgressBarLoading
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

    fun getUserDetail(mFragment: Fragment) {
        mFireStore.collection(constant.USER_COLLECTION).document(getCurrentUserAuth()!!.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val currentUser = document.toObject(User::class.java)!!
                    when(mFragment) {
                        is ProfileFragment -> {
                            mFragment.setCurrentUser(currentUser)
                            mFragment.ShowUIInfo(currentUser)
                        }
                        is HomeFragment -> {
                            mFragment.setCurrentUser(currentUser)
                            mFragment.ShowUIInfo(currentUser)
                        }
                    }
                } else {
                    Log.e("get database", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("get database", "get userDetail failed", exception)
            }
    }
    fun updateUserInfo(mFragment: Fragment, userHashMap: HashMap<String, Any>){
        mFireStore.collection(constant.USER_COLLECTION).document(getCurrentUserAuth()!!.uid)
            .update(userHashMap)
            .addOnSuccessListener {
                when(mFragment) {
                    is UpdateProfileFragment -> {
                        mFragment.updateUserSuccessConfirm()
                    }
                }
            }
            .addOnFailureListener{ e ->
                Log.e("update UserInfo", "update UserInfo failed", e)
            }
    }

}