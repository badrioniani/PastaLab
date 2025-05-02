package com.example.pastaorderapp.data

import com.google.gson.annotations.SerializedName

data class ApiKeyRequest(
    @SerializedName("apiLogin")val apiLogin:String
)