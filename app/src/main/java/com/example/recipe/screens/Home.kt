package com.example.recipe

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase.*
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.TypeConverters
import coil.compose.rememberImagePainter
import com.example.recipe.api.Recipe
import com.example.recipe.api.RecipeResponse
import com.example.recipe.api.RecipeService
import com.example.recipe.data.getAllData
import com.example.recipe.data.getSearch
import com.example.recipe.database.ListToStringConverter
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeDatabase
import com.example.recipe.database.RecipeDb
import kotlinx.coroutines.*

@Composable
@TypeConverters(ListToStringConverter::class)
fun Home(navController: NavController) {
    val recipeDatabase = RecipeDatabase.getDatabase(LocalContext.current.applicationContext)
    val recipeDao : RecipeDao = recipeDatabase.recipeDao()
    val scope = rememberCoroutineScope()
    val recipes = remember { mutableStateOf<RecipeResponse?>(null) }
    val searchText = remember { mutableStateOf("") }
    val isLoading = recipes.value == null
    val selectedCategory = remember { mutableStateOf("") }
    val IsConnected = remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        getAllData(
            recipes = recipes,
            IsConnected = IsConnected,
            scope = scope,
            recipeDao = recipeDao
        )
    }
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .width(400.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(50.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                label = {
                    Text(
                        text = "Searching...",
                        style = MaterialTheme.typography.body1.merge(TextStyle(fontSize = 18.sp))
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),

                trailingIcon = {
                    searchButton("Search") {
                        selectedCategory.value = searchText.value
                        getSearch(
                            searchText = selectedCategory.value,
                            recipes = recipes,
                            IsConnected = IsConnected,
                            scope = scope,
                            recipeDao = recipeDao
                        )
                    }
                }
            )
        }

        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
            item {
                CategoryButton("All", selectedCategory.value == "All") {
                    selectedCategory.value = "All"
                    getSearch(
                        searchText = selectedCategory.value,
                        recipes = recipes,
                        IsConnected = IsConnected,
                        scope = scope,
                        recipeDao = recipeDao
                    )
                }
                CategoryButton("Beef", selectedCategory.value == "Beef") {
                    selectedCategory.value = "Beef"
                    getSearch(
                        searchText = selectedCategory.value,
                        recipes = recipes,
                        IsConnected = IsConnected,
                        scope = scope,
                        recipeDao = recipeDao
                    )
                }
                CategoryButton("Chicken", selectedCategory.value == "Chicken") {
                    selectedCategory.value = "Chicken"
                    getSearch(
                        searchText = selectedCategory.value,
                        recipes = recipes,
                        IsConnected = IsConnected,
                        scope = scope,
                        recipeDao = recipeDao
                    )
                }
                CategoryButton("Carrot", selectedCategory.value == "Carrot") {
                    selectedCategory.value = "Carrot"
                    getSearch(
                        searchText = selectedCategory.value,
                        recipes = recipes,
                        IsConnected = IsConnected,
                        scope = scope,
                        recipeDao = recipeDao
                    )
                }
                CategoryButton("Soup", selectedCategory.value == "Soup") {
                    selectedCategory.value = "Soup"
                    getSearch(
                        searchText = selectedCategory.value,
                        recipes = recipes,
                        IsConnected = IsConnected,
                        scope = scope,
                        recipeDao = recipeDao
                    )
                }
                CategoryButton("French", selectedCategory.value == "French") {
                    selectedCategory.value = "French"
                    getSearch(
                        searchText = selectedCategory.value,
                        recipes = recipes,
                        IsConnected = IsConnected,
                        scope = scope,
                        recipeDao = recipeDao
                    )
                }

            }
        }
        if (isLoading) {
            loader()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                if (recipes.value?.results?.isNotEmpty() == true) {
                    items(recipes.value?.results ?: emptyList()) { recipe ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor = Color.White,
                            elevation = 4.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { navController.navigate("recipe/${recipe.pk}") }
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Image(
                                    painter = rememberImagePainter(recipe.featuredImage),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                )

                                Text(
                                    text = recipe.title,
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                } else {
                    item {
                        Text(
                            text = "No products found",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun loader(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
@Composable
fun searchButton(
    category: String,
    onSelected: (String) -> Unit
)
{
    Icon(
        Icons.Filled.Search,
        contentDescription = "Search",
        tint = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
    )
    IconButton(
        onClick = { onSelected(category) }
    ) {
        Icon(Icons.Filled.Search, contentDescription = "Search")
    }
}
@Composable
fun CategoryButton(
    category: String,
    isSelected: Boolean,
    onSelected: (String) -> Unit
) {
    Button(
        onClick = { onSelected(category) },
        colors = if (isSelected) {
            ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        } else {
            ButtonDefaults.buttonColors()
        },
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .height(40.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(25.dp))

    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body1.merge(TextStyle(fontSize = 18.sp)),
            color = if (isSelected) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onBackground
        )
    }
}