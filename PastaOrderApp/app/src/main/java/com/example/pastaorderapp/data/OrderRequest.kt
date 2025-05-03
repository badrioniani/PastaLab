package com.example.pastaorderapp.data

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("organizationId")val organizationId: String,
    @SerializedName("terminalGroupId")val terminalGroupId: String,
    @SerializedName("order")val order: Order,
    @SerializedName("createOrderSettings")val createOrderSettings: CreateOrderSettings
)

data class Order(
    @SerializedName("id")val id: String?,
    @SerializedName("externalNumber")val externalNumber: String?,
    @SerializedName("tableIds")val tableIds: List<String>?,
    @SerializedName("customer")val customer: String?,
    @SerializedName("phone")val phone: String?,
    @SerializedName("guestCount")val guestCount: Int,
    @SerializedName("guests")val guests: Guests,
    @SerializedName("tabName")val tabName: String?,
    @SerializedName("menuId")val menuId: String?,
    @SerializedName("items")val items: List<Item>,
    @SerializedName("combos")val combos: List<Any>,
    @SerializedName("payments")val payments: List<Any>,
    @SerializedName("tips")val tips: List<Any>,
    @SerializedName("sourceKey")val sourceKey: String?,
    @SerializedName("discountsInfo")val discountsInfo: String?,
    @SerializedName("loyaltyInfo")val loyaltyInfo: String?,
    @SerializedName("orderTypeId")val orderTypeId: String?,
    @SerializedName("chequeAdditionalInfo")val chequeAdditionalInfo: String?,
    @SerializedName("externalData")val externalData: List<Any>
)

data class Guests(
    @SerializedName("count")val count: Int
)

data class Item(
    @SerializedName("productId")val productId: String,
    @SerializedName("modifiers")val modifiers: List<Modifier>? = null,
    @SerializedName("type")val type: String = "Product",
    @SerializedName("amount")val amount: Int = 1,
    @SerializedName("price")val price: Double,
    @SerializedName("productSizeId")val productSizeId: String? = null,
    @SerializedName("comboInformation")val comboInformation: String? = null,
    @SerializedName("comment")val comment: String
)

data class Modifier(
    @SerializedName("productId")val productId: String,
    @SerializedName("amount")val amount: Int,
    @SerializedName("productGroupId")val productGroupId: String?,
    @SerializedName("price")val price: Double,
    @SerializedName("positionId")val positionId: String?
)

data class CreateOrderSettings(
    @SerializedName("servicePrint")val servicePrint: Boolean,
    @SerializedName("transportToFrontTimeout")val transportToFrontTimeout: Int,
    @SerializedName("checkStopList")val checkStopList: Boolean
)
