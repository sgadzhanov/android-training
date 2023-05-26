package com.example.mobiletraining.di

import com.example.mobiletraining.api.API
import com.example.mobiletraining.api.APIClient
import com.example.mobiletraining.api.DefaultRepository
import com.example.mobiletraining.api.TokenInterceptor
import com.example.mobiletraining.api.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    @Singleton
    fun providesJWTTokenProvider(): TokenProvider {
        return TokenProvider()
    }

    @Provides
    @Singleton
    fun providesJWTInterceptor(jwtTokenProvider: TokenProvider): TokenInterceptor {
        return TokenInterceptor(jwtTokenProvider)
    }

    @Provides
    @Singleton
    fun providesAPI(providesJWTInterceptor: TokenInterceptor): API {
        return APIClient(providesJWTInterceptor).defaultService
    }

    @Provides
    @Singleton
    fun providesRepository(apiService: API) = DefaultRepository(apiService)
}