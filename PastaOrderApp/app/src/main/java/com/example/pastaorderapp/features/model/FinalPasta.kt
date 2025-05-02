package com.example.pastaorderapp.features.model

data class PastaType(val name: String,val img:Int,var isSelected: Boolean = false)
data class Sauce(val name: String,val img:Int, val forPasta: String,var isSelected: Boolean = false)
data class Ingredient(val name: String,val img:Int, val forPasta: String, val forSauce: String,var isSelected: Boolean = false)

data class FinalPasta(
    val pasta: PastaType,
    val sauce: Sauce,
    val extras: List<Ingredient>
)