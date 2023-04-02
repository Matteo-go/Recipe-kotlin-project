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

fun getAllData(
    recipes: MutableState<RecipeResponse?>,
    IsConnected: MutableState<Boolean>,
    scope: CoroutineScope,
    recipeDao: RecipeDao,
    page: Int,
    hasMoreResults: MutableState<Boolean>
){
    scope.launch {
        try {
            var newResult = RecipeService().getRecipes(page)
            var oldRecipeList = recipes.value?.results ?: emptyList()
            var allRecipeList = oldRecipeList + newResult.results
            recipes.value = RecipeResponse(
                newResult.count,
                newResult.next,
                allRecipeList.distinct()
            )
            IsConnected.value = true
            withContext(Dispatchers.IO) {
                for (recipe in allRecipeList.distinct()) {
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
            if (oldRecipeList.distinct().size == allRecipeList.distinct().size) {
                hasMoreResults.value = false
            }
        } catch (e: Exception) {
            IsConnected.value = false
        }
        var offset = (page - 1) * 30
        if (!IsConnected.value) {
            val recipeDbList = recipeDao.getAll(30, offset)
            val oldRecipeList = recipes.value?.results ?: emptyList()
            val allRecipeList = oldRecipeList + recipeDbList.map { recipeDb ->
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
            }
            recipes.value = RecipeResponse(0, "", allRecipeList.distinct())
            if( oldRecipeList.distinct().size == allRecipeList.distinct().size){
                hasMoreResults.value = false
            }
        }
    }
}