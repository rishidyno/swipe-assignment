package com.rishi.swipe.assignment.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "products_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val price: Double,
    val productName: String,
    val productType: String,
    val tax: Double
) : Parcelable
