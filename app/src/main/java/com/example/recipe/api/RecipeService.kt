package com.example.recipe.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class RecipeService {
    companion object {
        private val client = HttpClientApi.getClient()
        private const val TOKEN = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
        private const val URL = "https://food2fork.ca/api"
    }

    suspend fun getRecipes(page: Int): RecipeResponse {
        val query = "beef%20carrot%20potato%20onion"
        val response = client.get("$URL/recipe/search/?page=$page&query=$query") {
            header("Authorization", TOKEN)
        }
        return response.body()
    }

    suspend fun getRecipe(id: Int): Recipe {
        val response = client.get("$URL/recipe/get/?id=$id") {
            header("Authorization", TOKEN)
        }
        return response.body()
    }

    suspend fun searchRecipes(query: String): RecipeResponse? {
        if (query == "All"){
            return getRecipes(2)
        }
        val response = client.get("$URL/recipe/search/?query=$query") {
            header("Authorization", TOKEN)
        }
        return if (response.status.isSuccess()) response.body() else null
    }
}