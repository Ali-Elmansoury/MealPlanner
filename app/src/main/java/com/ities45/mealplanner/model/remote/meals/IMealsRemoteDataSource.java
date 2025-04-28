package com.ities45.mealplanner.model.remote.meals;

public interface IMealsRemoteDataSource {
    // For Get meals by category
    void getMealsByCategoryNetworkCall(String category, IMealsNetworkCallback networkCallback);

    // For Get meals by area
    void getMealsByAreaNetworkCall(String area, IMealsNetworkCallback networkCallback);

    // For Get meals by ingredient
    void getMealsByIngredientNetworkCall(String ingredient, IMealsNetworkCallback networkCallback);

    // For Search meals by name
    void searchMealsByNameNetworkCall(String mealName, IMealsNetworkCallback networkCallback);

    // For Search meals by first letter
    void searchMealsByFirstLetterNetworkCall(String firstLetter, IMealsNetworkCallback networkCallback);

    // For Lookup full meal details by ID
    void getMealDetailsByIdNetworkCall(String mealId, IMealsNetworkCallback networkCallback);

    // For Get random meal
    void getRandomMealNetworkCall(IMealsNetworkCallback networkCallback);
}
