package com.rishi.swipe.assignment.model.database

import androidx.annotation.WorkerThread
import com.rishi.swipe.assignment.model.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val productDao: ProductDao) {

    @WorkerThread
    suspend fun cacheProductsToRoomDatabase(messages: List<ProductEntity>) {
        productDao.addProductToProductsTable(messages)
    }

    @WorkerThread
    suspend fun getAllCachedProductsFromDatBase() : Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }
}
