package com.example.recipe.data

import androidx.compose.runtime.MutableState
import com.example.recipe.api.Recipe
import com.example.recipe.api.RecipeResponse
import com.example.recipe.api.RecipeService
import com.example.recipe.database.RecipeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun getDataById(
    id: Int,
    recipe: MutableState<Recipe?>,
    IsConnected: MutableState<Boolean>,
    scope: CoroutineScope,
    recipeDao: RecipeDao

) {
    scope.launch {
        try {
            recipe.value = RecipeService().getRecipe(id)
            IsConnected.value = true
        } catch (e: Exception) {
            IsConnected.value = false
        }
        if (!IsConnected.value) {
            val recipeDb = recipeDao.getById(id)
            recipe.value = Recipe(
                recipeDb.pk,
                recipeDb.title,
                recipeDb.description,
                recipeDb.ingredients,
                recipeDb.publisher,
                recipeDb.sourceUrl,
                recipeDb.featuredImage,
                recipeDb.rating,
                recipeDb.dateAdded,
                recipeDb.dateUpdated,
                recipeDb.longDateAdded,
                recipeDb.longDateUpdated
            )
        }
    }
}