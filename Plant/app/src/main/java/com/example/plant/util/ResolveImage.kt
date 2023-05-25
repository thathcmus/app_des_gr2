package com.example.plant.util

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import com.example.plant.constant.constant

class ResolveImage {
    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(galleryIntent, constant.PICK_IMAGE_REQUEST_CODE)
    }
}