package com.rishi.swipe.assignment.model.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.rishi.swipe.assignment.model.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Upsert
    suspend fun addProductToProductsTable(product:List<ProductEntity>)

    @Query("SELECT * FROM products_table")
    fun getAllProducts() : Flow<List<ProductEntity>>
}