package com.example.mobiletraining.api

import com.example.mobiletraining.models.ProductModel
import com.example.mobiletraining.models.UserRequest
import com.example.mobiletraining.models.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    @GET(value = "products/2")
    suspend fun getProductDetails(): ProductModel

    @POST(value = "auth/local")
    suspend fun login(@Body request: UserRequest): UserResponse
}