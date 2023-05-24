package com.example.plant.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var id: String = "",  var fullName: String = "", var email : String = "", var phone: Long = 0, var gender : String = "", var location: String = "",
           var avatar : String = ""): Parcelable{}