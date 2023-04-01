package com.example.recipe.data

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import com.example.recipe.api.Recipe
import com.example.recipe.api.RecipeResponse
import com.example.recipe.api.RecipeService
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
fun getAllData(
    recipes: MutableState<RecipeResponse?>,
    IsConnected: MutableState<Boolean>,
    scope: CoroutineScope,
    recipeDao: RecipeDao
){
    scope.launch {
        try {
            recipes.value = RecipeService().getRecipes()
            IsConnected.value = true
            withContext(Dispatchers.IO) {
                for (recipe in recipes.value!!.results) {
                    val recipeDb = RecipeDb(
                        recipe.pk,
                        recipe.title,
                        recipe.description,
                        recipe.ingredients,
                        recipe.publisher,
                        recipe.sourceUrl,
                        recipe.featuredImage,
                        recipe.rating,
                        recipe.dateAdded,
                        recipe.dateUpdated,
                        recipe.longDateAdded,
                        recipe.longDateUpdated
                    )
                    recipeDao.insert(recipeDb)
                }
            }
        } catch (e: Exception) {
            IsConnected.value = false
        }
        if (!IsConnected.value) {
            val recipeDbList = recipeDao.getAll()
            recipes.value = RecipeResponse(0, "", recipeDbList.map { recipeDb ->
                Recipe(
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
            })
        }
    }
}