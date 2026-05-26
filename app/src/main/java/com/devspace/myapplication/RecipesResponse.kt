package com.devspace.myapplication

data class RecipesResponse(
    val recipes: List<RecipeDto>
)

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String? = null
)

data class IngredientDto(
    val id: Int,
    val name: String,
    val original: String,
)

data class RecipeDetailDto(
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    val instructions: String,
    val cookingMinutes: Int,
    val preparationMinutes: Int,
    val healthScore: Int,
    val extendedIngredients: List<IngredientDto>
)
