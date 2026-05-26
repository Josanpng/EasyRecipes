package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Response
import androidx.compose.foundation.lazy.items
import androidx.core.text.parseAsHtml

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    onBackClick: () -> Unit,
    apiService: ApiService
) {
    var recipeDetail by remember {
        mutableStateOf<RecipeDetailDto?>(null)
    }

    LaunchedEffect(Unit) {
        val callRecipeDetail = apiService.getRecipeDetail(recipeId)
        callRecipeDetail.enqueue(object : retrofit2.Callback<RecipeDetailDto> {
            override fun onResponse(
                call: Call<RecipeDetailDto?>,
                response: Response<RecipeDetailDto?>
            ) {
                if (response.isSuccessful) {
                    val recipe = response.body()
                    if (recipe != null) {
                        recipeDetail = recipe
                    }
                } else {
                    Log.d(
                        "RecipeDetailScreen",
                        "Request Error :: code=${response.code()} body=${
                            response.errorBody()?.string()
                        }"
                    )
                }
            }

            override fun onFailure(
                call: Call<RecipeDetailDto?>,
                t: Throwable
            ) {
                Log.d("RecipeDetailScreen", "Network Error :: ${t.message}")
            }
        })
    }

    Surface() {

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            if (recipeDetail == null) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                val detail = recipeDetail ?: return@Box
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(
                                    top = 30.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                )
                                .fillMaxWidth(),
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .height(200.dp),
                                contentScale = ContentScale.Crop,
                                model = detail.image,
                                contentDescription = "${detail.title} Recipe Image"
                            )

                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.tertiary,
                                        shape = CircleShape
                                    )
                                    .size(36.dp)
                                    .padding(8.dp),
                                onClick = onBackClick,

                                ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back Button",
                                    tint = Color.White

                                )
                            }

                        }
                    }
                    item {

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = detail.title,
                            style = MaterialTheme.typography.titleLarge.copy(

                            )
                        )
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                            ) {
                                Text(
                                    text = "Ready in ${detail.readyInMinutes} minutes",
                                    style = MaterialTheme.typography.titleSmall.copy(

                                    )
                                )
                                Text(
                                    text = " | ",
                                    style = MaterialTheme.typography.titleSmall.copy(

                                    )
                                )
                                Text(
                                    text = "${detail.servings} servings",
                                    style = MaterialTheme.typography.titleSmall.copy(

                                    )
                                )
                                Text(
                                    text = " | ",
                                    style = MaterialTheme.typography.titleSmall.copy(

                                    )
                                )
                                Text(
                                    text = "${detail.healthScore}% health score",
                                    style = MaterialTheme.typography.titleSmall.copy(

                                    )
                                )

                            }
                        }
                    }

                    item {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    items(detail.extendedIngredients) { ingredient ->
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            text = "• ${ingredient.original}"
                        )

                    }
                    item {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Instructions",
                            style = MaterialTheme.typography.titleMedium
                        )

                    }
                    item {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            text = detail.instructions.parseAsHtml().toString(),
                        )
                    }


                }
            }

        }
    }
}



