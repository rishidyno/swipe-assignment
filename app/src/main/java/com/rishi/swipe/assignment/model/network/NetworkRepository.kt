package com.rishi.swipe.assignment.model.network

import android.util.Log
import com.rishi.swipe.assignment.model.entity.AddProductResponse
import com.rishi.swipe.assignment.model.entity.ProductDTOItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val apiService: ProductApiService,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getAllProducts(): Result<MutableList<ProductDTOItem>?> {
        return withContext(dispatcher) {
            return@withContext try {
                val response = apiService.getProducts()
                val productResponse: MutableList<ProductDTOItem>? = response.body()

                if (response.isSuccessful) {
                    Result.success(productResponse)
                } else {
                    Result.failure(Throwable("Bad Network"))
                }
            } catch (e: IllegalStateException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun postProduct(productDTOItem: ProductDTOItem): Result<AddProductResponse?> {
        return withContext(dispatcher) {
            try {
                val response = apiService.addAProduct(
                    "",
                    productDTOItem.price,
                    productDTOItem.productName,
                    productDTOItem.productType,
                    productDTOItem.tax
                )
                val productResponse: AddProductResponse? = response.body()

                if (response.isSuccessful) {
                    Log.e("tag","Successful")
                    Result.success(productResponse)
                } else {
                    Log.e("tag","Error bad network")
                    Result.failure(Throwable("Bad Network"))
                }
            } catch (e: IllegalStateException) {
                Log.e("tag","error is",e)
                Result.failure(e)
            } catch (e: Exception) {

                Log.e("tag","error is",e)
                Result.failure(e)
            }
        }
    }
}
