package com.example.pastaorderapp.data

import com.example.pastaorderapp.di.ExampleRepository
import okhttp3.Response
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: ExampleRepository
) {
    suspend operator fun invoke(token: String,orderRequest: OrderRequest): Any {
        return repository.createOrder(token,orderRequest)
    }
}