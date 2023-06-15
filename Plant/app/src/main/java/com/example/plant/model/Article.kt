package com.example.plant.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var id : String = "",
    var image: String = "",
    var title: String = "",
    var content: String = "",
    var posterAvatar: String = "",
    var posterName: String = "",
    var createdAt: String = "",
    var status: List<String> = emptyList()
) :
    Parcelable {}