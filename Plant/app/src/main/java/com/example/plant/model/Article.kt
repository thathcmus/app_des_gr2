package com.example.plant.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var image: Int = 0,
    var title: String = "",
    var name: String = "",
    var day: String = "",
    var avatar: Int = 0,
    var descriptionArticles: String = "",
    var detailArticles: String = ""
) :
    Parcelable {}
