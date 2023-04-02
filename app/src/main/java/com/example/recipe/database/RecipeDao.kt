package com.example.recipe.database

import androidx.room.*

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipeDb Limit :limit Offset :offset")
    suspend fun getAll(limit: Int, offset: Int) : List<RecipeDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe : RecipeDb)

    @Query("SELECT * FROM recipeDb WHERE pk = :pk")
    suspend fun getById(pk: Int) : RecipeDb

    @Delete
    suspend fun delete(recipe : RecipeDb)

    @Query("SELECT * FROM recipeDb WHERE ingredients LIKE '%' || :query || '%'")
    suspend fun search(query: String) : List<RecipeDb>
}