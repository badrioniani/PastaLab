package com.example.pastaorderapp.di

import com.example.pastaorderapp.data.ApiKeyRequest
import com.example.pastaorderapp.data.ExampleResponse
import com.example.pastaorderapp.data.OrderRequest
import com.example.pastaorderapp.data.SyrveApiService
import okhttp3.Response

class ExampleRepositoryImpl(
    private val apiService: SyrveApiService
) : ExampleRepository {

    override suspend fun getExamples(): ExampleResponse {
        val response =
            apiService.getExampleData(ApiKeyRequest(apiLogin = "5da1f78c0df744f3ababc3930953b4bc"))
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty Body")
        } else {
            throw Exception("API error: ${response.code()}")
        }
    }

    override suspend fun createOrder(token: String,orderRequest: OrderRequest): Response {
        val response = apiService.createOrder(
            token = token,
            timeout = "15",
            request = orderRequest
        )
        if (response.isSuccessful){
            return (response.body() ?: throw Exception("Empty Body")) as Response
        } else {
            throw Exception("API error: ${response.code()}")
        }
    }
}
