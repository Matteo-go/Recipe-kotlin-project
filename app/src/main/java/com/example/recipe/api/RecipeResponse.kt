package com.example.recipe.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String,
    //@SerialName("previous")
    //val previous: String,
    @SerialName("results")
    val results: List<Recipe>
)