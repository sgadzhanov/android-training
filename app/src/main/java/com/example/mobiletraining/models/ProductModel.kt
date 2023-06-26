package com.example.mobiletraining.models

data class ProductModel(
    val id: Int,
    val title: String,
    val description: String,
    val short_description: String,
    val stock: Int,
    val price: Int,
    val rating: Int,
    val image: String,
    val category: String,
)