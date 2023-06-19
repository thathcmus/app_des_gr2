package com.example.plant.firestore

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.activities.RegisterAccount.RegisterActivity
import com.example.plant.adapter.ArticleRecyclerAdapter
import com.example.plant.constant.constant
import com.example.plant.fragment.HomeFragment
import com.example.plant.fragment.ProfileFragment
import com.example.plant.fragment.UpdateProfileFragment
import com.example.plant.model.Article
import com.example.plant.model.Plant
import com.example.plant.model.User
import com.example.plant.util.ProgressBarLoading
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
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
                            mFragment.showUIInfo(currentUser)
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

    fun getPlantListOfPlantType(plantTypeName: String, callback: (MutableList<Plant>) -> Unit) {
        val plantList: MutableList<Plant> = mutableListOf()
        FirebaseFirestore.getInstance().collection("plant")
            .whereEqualTo("plantType", plantTypeName)
            .get()
            .addOnSuccessListener { plantDocuments ->
                plantList.addAll(plantDocuments.toObjects(Plant::class.java))
                callback(plantList)
            }
            .addOnFailureListener { exception ->
                // Xử lý khi truy vấn thất bại
            }
    }

    fun getArticleIds(userId: String,callback: (ArrayList<String>) -> Unit){
        val plantIds : ArrayList<String> = ArrayList()
        FirebaseFirestore.getInstance().collection("likedPlant")
            .whereEqualTo(userId, true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    plantIds.add(document.id)
                }
                callback(plantIds)
            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting Plant Ids firetore: ", exception)
            }
    }

}