package com.example.plant.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Plant(
    var id : String = "",
    var image: String = "",
    var name: String = "",
    var description: String = "",
    var kingdom: String = "",
    var family: String = "",
    var plantType: String = "",
    var species: String = "",
    var star: String = "",
    var status: List<String> = emptyList()
) : Parcelable {}