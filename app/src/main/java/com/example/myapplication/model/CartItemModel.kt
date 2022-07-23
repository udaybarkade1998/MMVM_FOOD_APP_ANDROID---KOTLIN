package com.example.myapplication.model

data class CartItemModel(
    val itemId: Int,
    val name: String,
    val price: Float,
    var quantity: Int,
    val item_image: Int
)