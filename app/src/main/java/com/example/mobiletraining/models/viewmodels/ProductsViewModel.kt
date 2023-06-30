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
import com.example.mobiletraining.utils.constants.Constants.ProductsFilterConstants.max_categories_count
import com.example.mobiletraining.utils.constants.Constants.ProductsFilterConstants.max_price
import com.example.mobiletraining.utils.constants.Constants.ProductsFilterConstants.min_categories_count
import com.example.mobiletraining.utils.constants.Constants.ProductsFilterConstants.min_price
import com.example.mobiletraining.utils.constants.Constants.ProductsFilterConstants.min_rating
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val repository: DefaultRepository) :
    ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            getAllProducts()
            getAllCategories()
        }
    }

    private var tmpCurrentCount = MutableStateFlow(0)
    var activeFiltersCount: StateFlow<Int> = tmpCurrentCount

    private var allProducts = mutableStateListOf<ProductModel>()
    var filteredProducts = mutableStateListOf<ProductModel>()

    var textFilter by mutableStateOf("")
    private val tmpIsLoading = mutableStateOf(false)
    val isLoading: Boolean by tmpIsLoading

    var allCategories = mutableStateListOf<CategoryModel>()

    private suspend fun getAllProducts() {
        try {
            tmpIsLoading.value = true
            allProducts.addAll(repository.getAllProducts())
            filteredProducts.addAll(allProducts)
            allCategories.addAll(repository.getCategories())
        } catch (e: Exception) {
        } finally {
            tmpIsLoading.value = false
        }
    }

    fun removeAllFilters() {
        filteredProducts.apply {
            clear()
            addAll(allProducts)
        }
    }

    private suspend fun getAllCategories() {
        allCategories.clear()
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
        filteredProducts.apply {
            clear()
            addAll(allProducts.filter { product ->
                selectedCategories
                    .contains(product.category)
                        && product.price >= price.start
                        && product.price <= price.endInclusive
                        && product.rating >= rating
            })
        }
        calculateActiveFiltersCount(selectedCategories, rating, price)
    }

    private fun calculateActiveFiltersCount(
        selectedCategories: MutableList<String>,
        rating: Int,
        price: ClosedFloatingPointRange<Float>,
    ) {
        var count = 0

        if (rating != min_rating) count++
        if (selectedCategories.count() != max_categories_count || selectedCategories.count() != min_categories_count) count++
        if (price.start != min_price || price.endInclusive != max_price) count++

        tmpCurrentCount.value = count
    }
}