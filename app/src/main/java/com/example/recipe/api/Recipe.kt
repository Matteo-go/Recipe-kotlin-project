package com.example.recipe.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    //@SerialName("cooking_instructions")
    //val cookingInstructions: Any,
    @SerialName("date_added")
    val dateAdded: String,
    @SerialName("date_updated")
    val dateUpdated: String,
    @SerialName("description")
    val description: String,
    @SerialName("featured_image")
    val featuredImage: String,
    @SerialName("ingredients")
    val ingredients: List<String>,
    @SerialName("long_date_added")
    val longDateAdded: Int,
    @SerialName("long_date_updated")
    val longDateUpdated: Int,
    @SerialName("pk")
    val pk: Int,
    @SerialName("publisher")
    val publisher: String,
    @SerialName("rating")
    val rating: Int,
    @SerialName("source_url")
    val sourceUrl: String,
    @SerialName("title")
    val title: String
)