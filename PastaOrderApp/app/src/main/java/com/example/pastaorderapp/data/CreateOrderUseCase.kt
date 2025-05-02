package com.example.pastaorderapp.data

import com.example.pastaorderapp.di.ExampleRepository
import okhttp3.Response

class CreateOrderUseCase(
    private val repository: ExampleRepository
) {
    suspend operator fun invoke(token: String,orderRequest: OrderRequest): Any {
        return repository.createOrder(token,orderRequest)
    }
}