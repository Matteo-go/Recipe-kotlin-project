package com.example.recipe.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
public class RecipeDb(
    @PrimaryKey val pk: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @TypeConverters(ListToStringConverter::class)
    @ColumnInfo(name = "ingredients") val ingredients: List<String>,
    @ColumnInfo(name = "publisher") val publisher: String,
    @ColumnInfo(name = "source_url") val sourceUrl: String,
    @ColumnInfo(name = "featured_image") val featuredImage: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "date_added") val dateAdded: String,
    @ColumnInfo(name = "date_updated") val dateUpdated: String,
    @ColumnInfo(name = "long_date_added") val longDateAdded: String,
    @ColumnInfo(name = "long_date_updated") val longDateUpdated: String,
)