package com.example.pastaorderapp.features.model

data class OrderModel(
    val id:Int,
    val orderId: String,
    val isTrend:Boolean,
    val name:String,
    val img:Int,
    val price:String,
    val isFire:Boolean,
)