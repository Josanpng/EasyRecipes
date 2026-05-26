package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object SearchResults : Screen("search_results")
    data class RecipeDetail(val id: Int) : Screen("recipe_detail/$id") {
        companion object {
            const val BASE_ROUTE = "recipe_detail/{id}"
        }
    }

}

@Composable
fun AppNavigation(
    randomRecipes: List<RecipeDto>,
    searchResults: List<RecipeDto>,
    searchBarQuery: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClick: (RecipeDto) -> Unit,
    apiService: ApiService,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                randomRecipes = randomRecipes,
                searchResults = searchResults,
                searchBarQuery = searchBarQuery,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                onClick = {recipe ->
                    navController.navigate(Screen.RecipeDetail(recipe.id).route)
                }
            )

        }

        composable(Screen.RecipeDetail.BASE_ROUTE) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("id")?.toInt() ?:0
            RecipeDetailScreen(
                recipeId = recipeId,
                apiService = apiService,
                onBackClick = { navController.popBackStack() }

            )

        }

    }
}