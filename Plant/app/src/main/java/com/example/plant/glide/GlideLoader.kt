package com.example.plant.glide

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.plant.R
import java.io.IOException

class GlideLoader(val mContext: Context) {
    fun LoadUserPicture(uri: Uri, imageView: ImageView ){
        try {
            Glide
                .with(mContext)
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        }catch ( e: IOException){
            e.printStackTrace()
        }
    }
}