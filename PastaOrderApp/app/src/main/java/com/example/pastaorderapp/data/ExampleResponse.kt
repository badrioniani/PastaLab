package com.example.pastaorderapp.data

import com.google.gson.annotations.SerializedName

data class ExampleResponse(
    @SerializedName("correlationId")val correlationId:String,
    @SerializedName("token")val token:String
)
