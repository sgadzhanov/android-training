package com.example.mobiletraining.models.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletraining.api.DefaultRepository
import com.example.mobiletraining.models.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val repository: DefaultRepository) :
    ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllProducts()
        }
    }

    private var allProducts = mutableStateListOf<ProductModel>()
    var filteredProducts = mutableStateListOf<ProductModel>()

    private suspend fun getAllProducts() {
        println("KUREC2")
        val tmp = repository.getAllProducts()
        println("KUREC" + tmp.size)
        allProducts.addAll(repository.getAllProducts())
        filteredProducts.addAll(allProducts)
    }

    fun filterProducts(text: String) {
        filteredProducts.clear()
        val productsToAdd = allProducts.filter { p ->
            p.title.lowercase().contains(text.lowercase())
        }
        filteredProducts.addAll(productsToAdd)
        println("Products added")
    }
}