package com.example.plant.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.example.plant.constant.constant
import kotlinx.coroutines.CoroutineScope

class ResolveImage {
    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(galleryIntent, constant.PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(mActivity: Activity, uri: Uri?): String? {

        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(mActivity.contentResolver.getType(uri!!))
    }
}