package com.example.recipe.database

import androidx.room.TypeConverter
import java.util.*

class ListToStringConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.split("/")?.map { it.trim() } ?: emptyList()
    }

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return list?.joinToString(separator = "/") ?: ""
    }
}