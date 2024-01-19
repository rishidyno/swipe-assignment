package com.rishi.swipe.assignment.model.mappers

import com.rishi.swipe.assignment.model.entity.ProductDTOItem
import com.rishi.swipe.assignment.model.entity.ProductEntity

fun ProductDTOItem.toProductEntity(id: Int = 0): ProductEntity {
    return ProductEntity(
        id = id,
        image = this.image,
        price = this.price,
        productName = this.productName,
        productType = this.productType,
        tax = this.tax
    )
}

fun ProductEntity.toProductDTOItem(): ProductDTOItem {
    return ProductDTOItem(
        image = this.image,
        price = this.price,
        productName = this.productName,
        productType = this.productType,
        tax = this.tax
    )
}