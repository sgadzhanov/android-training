package com.example.mobiletraining.models.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletraining.models.UserRequest
import com.example.mobiletraining.models.UserResponse
import com.example.mobiletraining.api.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val repository: DefaultRepository) : ViewModel() {
    private val userResponse = MutableStateFlow<Result<UserResponse>?>(null)
    val response: StateFlow<Result<UserResponse>?> = userResponse

    private val isLoadingState = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = isLoadingState

    fun login(request: UserRequest) {
        viewModelScope.launch {
            try {
                isLoadingState.value = true
                val loginResponse = repository.login(request)
                userResponse.value = Result.success(loginResponse)
            } catch(e: Exception) {
                userResponse.value = Result.failure(e)
            } finally {
                isLoadingState.value = false
            }
        }
    }
}