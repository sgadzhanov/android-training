package com.example.mobiletraining.api

import com.example.mobiletraining.api.API
import com.example.mobiletraining.api.APIHelper
import com.example.mobiletraining.models.ProductModel
import com.example.mobiletraining.models.UserRequest
import com.example.mobiletraining.models.UserResponse

class DefaultRepository(val apiService: API) : APIHelper {
    override suspend fun login(request: UserRequest): UserResponse = apiService.login(request)
    override suspend fun getProduct(): ProductModel = apiService.getProductDetails("*")
}