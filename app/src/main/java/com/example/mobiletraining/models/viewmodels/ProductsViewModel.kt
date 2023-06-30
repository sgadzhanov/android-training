package com.example.mobiletraining.models.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletraining.api.DefaultRepository
import com.example.mobiletraining.models.CategoryModel
import com.example.mobiletraining.models.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val repository: DefaultRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            getAllProducts()
            getAllCategories()
        }
    }

    private var allProducts = mutableStateListOf<ProductModel>()
    var filteredProducts = mutableStateListOf<ProductModel>()

    var textFilter by mutableStateOf("")
    private var tmpCurrentCount = MutableStateFlow(0)
    private val tmpIsLoading = mutableStateOf(false)
    val isLoading: Boolean by tmpIsLoading

    var allCategories = mutableStateListOf<CategoryModel>()

    private suspend fun getAllProducts() {
        try {
            tmpIsLoading.value = true
            allProducts.addAll(repository.getAllProducts())
            filteredProducts.addAll(allProducts)
            allCategories.addAll(repository.getCategories())
        } catch (e: Exception) {}
        finally {
            tmpIsLoading.value = false
        }
    }

    fun removeAllFilters() {
        filteredProducts.clear()
        filteredProducts.addAll(allProducts)
        tmpCurrentCount.value = 0
    }

    suspend fun getAllCategories() {
        allCategories.apply { this.clear() }
        allCategories.addAll(repository.getCategories())
    }

    fun filterProducts(text: String) {
        filteredProducts.clear()
        val productsToAdd = allProducts.filter { p ->
            p.title.lowercase().contains(text.lowercase())
        }
        filteredProducts.addAll(productsToAdd)
    }

    fun applyFilters(
        selectedCategories: MutableList<String>,
        rating: Int,
        price: ClosedFloatingPointRange<Float>,
    ) {
        filteredProducts.clear()
        filteredProducts.addAll(allProducts.filter { p ->
            selectedCategories
                .contains(p.category)
                    && p.price >= price.start
                    && p.price <= price.endInclusive
                    && p.rating >= rating
        })
        getActiveFiltersCount(selectedCategories, rating, price)
    }

    private fun getActiveFiltersCount(
        selectedCategories: MutableList<String>,
        rating: Int,
        price: ClosedFloatingPointRange<Float>,
    ) {
        var count = 0

        if (rating != 1) count++
        if (selectedCategories.count() != 4) count++
        if (price.start != 0f || price.endInclusive != 200f) count++

        tmpCurrentCount.value = count
    }
}