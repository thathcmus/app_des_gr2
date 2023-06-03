package com.example.plant

data class PlantTypeHomeModel(
    val name: String,   // Tên loại cây
    val image: String,  // URL của ảnh trong Storage
    var count: Int,     // Đếm số lượng cây cùng loại
) {
    constructor():this(
        "",
        "",
        0
    )
}