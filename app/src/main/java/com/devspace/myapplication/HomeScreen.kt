package com.devspace.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    randomRecipes: List<RecipeDto>,
    searchResults: List<RecipeDto>,
    searchBarQuery: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClick: (RecipeDto) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                text = "Find the best recipes\nfor cooking"
            )


            ERSearchBar(
                query = searchBarQuery,
                placeHolder = "Search recipes",
                onValueChange = onQueryChange,
                onSearch = onSearch

            )

            RecipesCategory(
                label = if (searchResults.isEmpty())"Recipes" else "Search Results",
                recipeList = if (searchResults.isEmpty()) randomRecipes else searchResults,
                onClick = onClick
            )

        }
    }
}


