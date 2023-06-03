package com.example.plant

data class PlantTypeModel(
    val name: String,
    val image: String,
    var count: Int,     // Đếm số lượng cây cùng loại
) {
    constructor():this(
        "",
        "",
        0
    )
}