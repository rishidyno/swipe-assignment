package com.rishi.swipe.assignment.model.entity

data class AddProductResponse(
    val message: String,
//    val product_details: ProductDetails,
    val product_id: Int,
    val success: Boolean
)