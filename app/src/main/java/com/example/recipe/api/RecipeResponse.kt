package com.example.recipe.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String,
    @SerialName("results")
    val results: List<Recipe>
) {
    operator fun plus(other: RecipeResponse): RecipeResponse {
        return RecipeResponse(
            count = count + other.count,
            next = other.next,
            //previous = other.previous,
            results = results + other.results
        )
    }
}