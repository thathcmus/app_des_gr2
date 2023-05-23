package com.example.plant.firestore

import android.widget.Toast
import com.example.plant.activities.RegisterAccount.RegisterActivity
import com.example.plant.constant.constant
import com.example.plant.model.User
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

}