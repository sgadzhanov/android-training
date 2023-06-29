package com.example.mobiletraining.api

import com.example.mobiletraining.models.CategoryModel
import com.example.mobiletraining.models.ProductModel
import com.example.mobiletraining.models.UserRequest
import com.example.mobiletraining.models.UserResponse

interface APIHelper {
    suspend fun login(request: UserRequest): UserResponse
    suspend fun getProduct(id: String): ProductModel
    suspend fun getAllProducts(): List<ProductModel>
    suspend fun getCategories(): List<CategoryModel>
}