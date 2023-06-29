package com.example.mobiletraining.models.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletraining.api.DefaultRepository
import com.example.mobiletraining.models.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(val repository: DefaultRepository) : ViewModel() {
    val product = mutableStateOf(null as ProductModel?)

    suspend fun getProduct(id: String) {
        product.value = repository.getProduct(id)
    }
}