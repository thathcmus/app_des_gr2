package com.example.plant

data class PhotographyHomeModel(
    val thumbnail: String,   // thumbnail của ảnh
    val picture: String,  // URL của ảnh trong Storage
) {
    constructor():this(
        "",
        ""
    )
}