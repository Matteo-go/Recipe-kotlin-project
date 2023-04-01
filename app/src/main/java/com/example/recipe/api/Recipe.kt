package com.example.recipe.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    //@SerialName("cooking_instructions")
    //val cookingInstructions: Any,
    @SerialName("pk")
    val pk: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("ingredients")
    val ingredients: List<String>,
    @SerialName("publisher")
    val publisher: String,
    @SerialName("source_url")
    val sourceUrl: String,
    @SerialName("featured_image")
    val featuredImage: String,
    @SerialName("rating")
    val rating: Int,
    @SerialName("date_added")
    val dateAdded: String,
    @SerialName("date_updated")
    val dateUpdated: String,
    @SerialName("long_date_added")
    val longDateAdded: String,
    @SerialName("long_date_updated")
    val longDateUpdated: String,
)