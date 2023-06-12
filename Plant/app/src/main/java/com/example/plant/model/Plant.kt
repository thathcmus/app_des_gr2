package com.example.plant.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Plant(
    var id : String = "",
    var image: String = "",
    var name: String = "",
    var content: String = "",
    var kingdom: String = "",
    var family: String = "",
    var plantType: String = "",
) :
    Parcelable {}