package com.ities45.mealplanner.model.repository.meals;

import com.ities45.mealplanner.model.remote.areas.IAreasNetworkCallback;
import com.ities45.mealplanner.model.remote.categories.ICategoriesNetworkCallback;
import com.ities45.mealplanner.model.remote.ingredients.I_IngredientsNetworkCallback;
import com.ities45.mealplanner.model.remote.meals.IMealsNetworkCallback;

public interface IMealsRepository {
    // For Get meals by category
    void getMealsByCategory(String category, IMealsNetworkCallback networkCallback);

    // For Get meals by area
    void getMealsByArea(String area, IMealsNetworkCallback networkCallback);

    // For Get meals by ingredient
    void getMealsByIngredient(String ingredient, IMealsNetworkCallback networkCallback);

    // For Search meals by name
    void searchMealsByName(String mealName, IMealsNetworkCallback networkCallback);

    // For Search meals by first letter
    void searchMealsByFirstLetter(String firstLetter, IMealsNetworkCallback networkCallback);

    // For Lookup full meal details by ID
    void getMealDetailsById(String mealId, IMealsNetworkCallback networkCallback);

    // For Get random meal
    void getRandomMeal(IMealsNetworkCallback networkCallback);

    void listAllAreasNames(IAreasNetworkCallback networkCallback);

    void listAllIngredients(I_IngredientsNetworkCallback networkCallback);

    void getAllMealCategories(ICategoriesNetworkCallback networkCallback);

    void listAllCategoriesNames(ICategoriesNetworkCallback networkCallback);
}
