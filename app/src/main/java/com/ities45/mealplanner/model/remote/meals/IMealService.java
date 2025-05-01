package com.ities45.mealplanner.model.remote.meals;

import com.ities45.mealplanner.model.pojo.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMealService {
    // Get meals by category
    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    // Get meals by area
    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);

    // Get meals by ingredient
    @GET("filter.php")
    Call<MealResponse> getMealsByIngredient(@Query("i") String ingredient);

    // Search meals by name
    @GET("search.php")
    Call<MealResponse> searchMealsByName(@Query("s") String mealName);

    // Search meals by first letter
    @GET("search.php")
    Call<MealResponse> searchMealsByFirstLetter(@Query("f") String firstLetter);

    // Lookup full meal details by ID
    @GET("lookup.php")
    Call<MealResponse> getMealDetailsById(@Query("i") String mealId);

    // Get random meal
    @GET("random.php")
    Call<MealResponse> getRandomMeal();
}
