package com.example.mobiletraining.models

data class UserModel(
    val id: Int,
    val username: String,
    val email: String,
    val provider: String,
    val confirmed: Boolean,
    val blocked: Boolean,
)

data class UserRequest(
    val identifier: String,
    val password: String,
)

data class UserResponse(
    val jwt: String,
    val user: UserModel,
)