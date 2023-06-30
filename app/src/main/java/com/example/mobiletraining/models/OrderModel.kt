package com.example.mobiletraining.models

import java.util.Date

data class OrderModel(val products: List<ProductModel>, val date: Date) {}