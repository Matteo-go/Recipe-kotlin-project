package com.example.recipe

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            Home(navController)
        }
        composable(route = "recipe/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                RecipePage(id, navController)
            }
        }
    }
}