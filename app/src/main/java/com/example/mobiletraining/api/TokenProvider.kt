package com.example.mobiletraining.api

class TokenProvider(
    var token: String? = null,
    var isLoggedIn: Boolean = false,
) {
    fun setJwtToken(token: String) {
        this.token = token
        this.isLoggedIn = true
    }

    fun getJwtToken(): String? = this.token

    fun getIsLoggedIn(): Boolean = this.isLoggedIn
}