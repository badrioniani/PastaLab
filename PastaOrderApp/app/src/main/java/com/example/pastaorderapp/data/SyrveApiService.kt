package com.example.pastaorderapp.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SyrveApiService {
    @POST("api/1/access_token")
    suspend fun getExampleData(@Body request: ApiKeyRequest): Response<ExampleResponse>

    @POST("api/1/order/create")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Header("Timeout") timeout: String,
        @Body request: OrderRequest
    ): Response<Any>
}