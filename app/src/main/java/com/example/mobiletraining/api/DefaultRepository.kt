package com.example.mobiletraining.api

import com.example.mobiletraining.models.CategoryModel
import com.example.mobiletraining.models.ProductModel
import com.example.mobiletraining.models.UserRequest
import com.example.mobiletraining.models.UserResponse

class DefaultRepository(val apiService: API) : APIHelper {
    override suspend fun login(request: UserRequest): UserResponse = apiService.login(request)
    override suspend fun getProduct(id: String): ProductModel = apiService.getProductDetails(id, "*")
    override suspend fun getAllProducts(): List<ProductModel> = apiService.getAllProducts("*")
    override suspend fun getCategories(): List<CategoryModel> = apiService.getCategories()
}