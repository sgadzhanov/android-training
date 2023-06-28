package com.example.mobiletraining.api

import com.example.mobiletraining.models.ProductModel
import com.example.mobiletraining.models.UserRequest
import com.example.mobiletraining.models.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET(value = "products/{id}")
    suspend fun getProductDetails(
        @Path("id") id: String,
        @Query("populate") value: String,
    ): ProductModel

    @POST(value = "auth/local")
    suspend fun login(@Body request: UserRequest): UserResponse

    @GET(value = "products")
    suspend fun getAllProducts(@Query("populate") value: String): List<ProductModel>
}