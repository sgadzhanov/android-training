package com.example.mobiletraining.models.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletraining.api.DefaultRepository
import com.example.mobiletraining.api.TokenProvider
import com.example.mobiletraining.models.UserRequest
import com.example.mobiletraining.models.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: DefaultRepository,
    private val tokenProvider: TokenProvider,
) : ViewModel() {

    var isInvalidInfo by mutableStateOf(false)

    private val tmpUser = mutableStateOf<UserResponse?>(null)
    val user: UserResponse? by tmpUser

    private val tmpIsLoading = mutableStateOf(false)
    val isLoading: Boolean by tmpIsLoading

    fun login(identifier: String, password: String) {
        val req = UserRequest(identifier, password)
        login(req)
    }

    private fun login(body: UserRequest) {
        viewModelScope.launch {
            try {
                tmpIsLoading.value = true
                val userInfo = repository.login(body)
                tmpUser.value = userInfo
                tokenProvider.setJwtToken(userInfo.jwt)
            } catch (e: Exception) {
                isInvalidInfo = true
            } finally {
                tmpIsLoading.value = false
            }
        }
    }
}