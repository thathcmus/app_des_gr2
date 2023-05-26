package com.example.plant.firebaseStorage

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.plant.activities.UpdateProfile.UpdateProfileActivity
import com.example.plant.constant.constant
import com.example.plant.util.ResolveImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class FirebaseStorage {
    suspend fun uploadImageToCloundStorage(mActivity: Activity, imageFileUri: Uri?) : String {
        var imageUrl : String = ""
        val sRef = Firebase.storage.reference.child(
            constant.USER_PROFILE_IMAGE + System.currentTimeMillis() + "."
            + ResolveImage().getFileExtension(
                mActivity,
                imageFileUri
            )
        )
        try {
            val taskSnapshot = sRef.putFile(imageFileUri!!).await()
            val downloadUrl = taskSnapshot.metadata!!.reference!!.downloadUrl.await()
            imageUrl = downloadUrl.toString()
        } catch (exception: Exception) {
            Log.e(
                mActivity.javaClass.simpleName,
                exception.message,
                exception
            )
        }
        return imageUrl
    }
}