package com.example.mobiletraining

import androidx.compose.runtime.mutableStateListOf
import com.example.mobiletraining.models.OrderModel
import com.example.mobiletraining.models.ProductModel
import java.util.Date

class GlobalState {
    companion object {
        var cartProducts = mutableStateListOf<ProductModel>()
        var orders = mutableStateListOf<OrderModel>()

        fun addProduct(product: ProductModel) = cartProducts.add(product)
        fun removeProduct(id: Int) = cartProducts.removeIf { it.id == id }
        fun placeOrder() {
            orders.add(OrderModel(products = cartProducts, date = Date()))
            cartProducts.clear()
        }
    }
}