package com.example.plant.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Species (
    var id : String = "",
    var name : String = "",
):
    Parcelable{}