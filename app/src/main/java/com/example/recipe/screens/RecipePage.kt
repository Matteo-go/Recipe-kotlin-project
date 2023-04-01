package com.example.recipe

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.recipe.api.Recipe
import com.example.recipe.api.RecipeResponse
import com.example.recipe.api.RecipeService
import com.example.recipe.data.getDataById
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeDatabase
import kotlinx.coroutines.launch

@Composable
fun RecipePage(id: Int, navController: NavController) {
    val scope = rememberCoroutineScope()
    val recipe = remember { mutableStateOf<Recipe?>(null) }
    val isLoading = recipe.value == null
    val recipeDatabase = RecipeDatabase.getDatabase(LocalContext.current.applicationContext)
    val recipeDao : RecipeDao = recipeDatabase.recipeDao()
    val IsConnected = remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        getDataById(id, recipe, IsConnected, scope, recipeDao)
    }
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                TopAppBar(
                    title = { Text(text = recipe.value?.title ?: "") },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier
                                .clickable { navController.navigate("Home") }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
            item {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.White,
                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Image(
                            painter = rememberImagePainter(recipe.value?.featuredImage ?: ""),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        Text(
                            text = "Ingredients:",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                        )

                        for (ingredient in recipe.value?.ingredients ?: emptyList()) {
                            Text(
                                text = "- $ingredient",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 32.dp, top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
