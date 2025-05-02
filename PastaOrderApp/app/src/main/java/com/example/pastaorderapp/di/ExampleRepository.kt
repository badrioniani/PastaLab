package com.example.pastaorderapp.di

import com.example.pastaorderapp.data.ExampleResponse
import com.example.pastaorderapp.data.OrderRequest

interface ExampleRepository {
    suspend fun getExamples(): ExampleResponse
    suspend fun createOrder(token: String,orderRequest: OrderRequest): Any
}
