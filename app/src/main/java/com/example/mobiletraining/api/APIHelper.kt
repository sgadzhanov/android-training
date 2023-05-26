package com.example.mobiletraining.api

import com.example.mobiletraining.models.ProductModel
import com.example.mobiletraining.models.UserRequest
import com.example.mobiletraining.models.UserResponse

interface APIHelper {
    suspend fun login(request: UserRequest): UserResponse
    suspend fun getProduct(): ProductModel
}