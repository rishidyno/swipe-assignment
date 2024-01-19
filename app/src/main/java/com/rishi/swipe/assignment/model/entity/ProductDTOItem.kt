package com.rishi.swipe.assignment.model.entity

import com.squareup.moshi.Json

data class ProductDTOItem(
    val image: String,
    val price: Double,
    @Json(name = "product_name")
    val productName: String,
    @Json(name = "product_type")
    val productType: String,
    val tax: Double
)