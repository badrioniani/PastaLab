package com.example.pastaorderapp.features.model

import com.example.pastaorderapp.data.Modifier

data class PastaType(val name: String,val img:Int,var isSelected: Boolean = false,val orderId: String,val price: Double)
data class Sauce(val name: String,val img:Int, val forPasta: String,var isSelected: Boolean = false,val orderId: String = "",val price: Double = 0.00)
data class Ingredient(val name: String,val img:Int, val forPasta: String, val forSauce: String,var isSelected: Boolean = false,val orderId: String = "",val price: Double = 0.00)

data class FinalPasta(
    val pasta: PastaType,
    val sauce: Sauce,
    val extras: List<Modifier>
)