package com.example.mobiletraining.api

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (tokenProvider.getIsLoggedIn() && tokenProvider.token != null) {
            val tokenRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${tokenProvider.getJwtToken()}")
                .build()
            return chain.proceed(tokenRequest)
        }
        return chain.proceed(originalRequest)
    }

}