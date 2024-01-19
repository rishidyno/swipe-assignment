package com.rishi.swipe.assignment.model.network

import com.rishi.swipe.assignment.model.entity.AddProductResponse
import com.rishi.swipe.assignment.model.entity.ProductDTOItem
import com.rishi.swipe.assignment.model.entity.ProductEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductApiService {


    @GET("get")
    suspend fun getProducts(): Response<MutableList<ProductDTOItem>>

    @POST("add")
    suspend fun addAProduct(
        @Body productDTOItem: ProductDTOItem
    ):Result<AddProductResponse>

    @Multipart
    @POST("add")
    suspend fun addAProduct(
        @Part("image") image : String,
        @Part("price") price : Double,
        @Part("product_name") productName: String,
        @Part("product_type") productType : String,
        @Part("tax") tax : Double
    ): Response<AddProductResponse>

}

