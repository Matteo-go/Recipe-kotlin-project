package com.example.recipe.data

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import com.example.recipe.api.Recipe
import com.example.recipe.api.RecipeResponse
import com.example.recipe.api.RecipeService
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
fun getSearch(
    searchText: String,
    recipes: MutableState<RecipeResponse?>,
    IsConnected: MutableState<Boolean>,
    scope: CoroutineScope,
    recipeDao: RecipeDao
){
    scope.launch {
        try {
            if (searchText == "All") {
                recipes.value = RecipeService().getRecipes(2)
            } else {
                recipes.value = RecipeService().searchRecipes(searchText)
            }
            IsConnected.value = true
        } catch (e: Exception) {
            IsConnected.value = false
        }
        if (!IsConnected.value) {
            var recipeDbList = listOf<RecipeDb>()
            if(searchText == "All"){
                recipeDbList = recipeDao.getAll(30, 0)
            } else {
                recipeDbList = recipeDao.search(searchText)
            }
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