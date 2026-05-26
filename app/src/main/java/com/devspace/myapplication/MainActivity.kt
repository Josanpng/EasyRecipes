package com.devspace.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.parseAsHtml
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import retrofit2.Call
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

        setContent {
            EasyRecipesTheme {


                var searchResults by remember {
                    mutableStateOf<List<RecipeDto>>(emptyList())
                }

                var searchBarQuery by remember {
                    mutableStateOf<String>("")
                }

                var randomRecipes by remember {
                    mutableStateOf<List<RecipeDto>>(emptyList())
                }

                val onSearch = {
                    val callSearch = apiService.searchRecipes(searchBarQuery)

                    callSearch.enqueue(object :
                        retrofit2.Callback<SearchRecipeResponse> {
                        override fun onResponse(
                            call: Call<SearchRecipeResponse?>,
                            response: Response<SearchRecipeResponse?>
                        ) {
                            if (response.isSuccessful) {
                                val recipe = response.body()?.results
                                if (recipe != null) {
                                    searchResults = recipe

                                }
                            } else {
                                Log.d(
                                    "MainActivity",
                                    "Request Error :: ${response.errorBody()}"
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<SearchRecipeResponse?>,
                            t: Throwable
                        ) {
                            Log.d("MainActivity", "Network Error :: ${t.message}")
                        }
                    })
                }

                LaunchedEffect(Unit) {
                    val callRandomRecipes = apiService.getRandomRecipes()
                    callRandomRecipes.enqueue(object : retrofit2.Callback<RecipesResponse> {
                        override fun onResponse(
                            call: Call<RecipesResponse?>,
                            response: Response<RecipesResponse?>
                        ) {
                            if (response.isSuccessful) {
                                val recipe = response.body()?.recipes
                                if (recipe != null) {
                                    runOnUiThread {
                                        randomRecipes = recipe
                                    }
                                }


                            } else {
                                Log.d(
                                    "MainActivity",
                                    "Request Error :: code=${response.code()} body=${
                                        response.errorBody()?.string()
                                    }"
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<RecipesResponse?>,
                            t: Throwable
                        ) {
                            Log.d("MainActivity", "Network Error :: ${t.message}")
                        }
                    })
                }

                AppNavigation(
                    randomRecipes = randomRecipes,
                    searchResults = searchResults,
                    searchBarQuery = searchBarQuery,
                    onQueryChange = { searchBarQuery = it },
                    onSearch = onSearch,
                    onClick = { },
                    apiService = apiService
                )

            }

        }

    }

}

@Composable
fun RecipesCategory(
    label: String,
    recipeList: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 20.sp,
            text = label,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(8.dp))
        RecipeList(recipeList = recipeList, onClick = onClick)
    }


}

@Composable
fun RecipeList(
    recipeList: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(recipeList) {
            RecipeItem(
                recipeDto = it,
                onClick = onClick
            )
        }

    }


}

@Composable
fun RecipeItem(
    recipeDto: RecipeDto,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke(recipeDto) }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .height(200.dp),
            contentScale = ContentScale.Crop,
            model = recipeDto.image,
            contentDescription = "${recipeDto.title} Recipe Image"
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = recipeDto.title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = recipeDto.summary?.parseAsHtml()?.toString() ?: "",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3


        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

