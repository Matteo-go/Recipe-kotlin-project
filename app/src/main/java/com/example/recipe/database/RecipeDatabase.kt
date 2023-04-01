package com.example.recipe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeDb::class], version = 2, exportSchema = true)
@TypeConverters(ListToStringConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            if(INSTANCE == null){
                 INSTANCE = Room.databaseBuilder(context.applicationContext,
                     RecipeDatabase::class.java,
                    "recipe_database"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }
}