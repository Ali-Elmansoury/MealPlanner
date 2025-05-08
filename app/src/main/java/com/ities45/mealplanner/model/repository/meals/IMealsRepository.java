package com.ities45.mealplanner.model.repository.meals;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.ListenerRegistration;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.areas.IAreasNetworkCallback;
import com.ities45.mealplanner.model.remote.categories.ICategoriesNetworkCallback;
import com.ities45.mealplanner.model.remote.firebase.firestore.IFirestoreCallback;
import com.ities45.mealplanner.model.remote.ingredients.I_IngredientsNetworkCallback;
import com.ities45.mealplanner.model.remote.meals.IMealsNetworkCallback;

import java.util.List;

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


    boolean isNetworkAvailable();
    void registerNetworkCallback();
    void unregisterNetworkCallback();


    void insertLocalMeal(Meal meal);
    void insertLocalAllMeals(List<Meal> meals);
    LiveData<List<Meal>> getAllLocalStoredMeals();
    LiveData<Meal> getLocalMealById(String id);
    void deleteLocalMeal(Meal meal);
    void deleteAllLocalMeals();
    void updateLocalMeal(Meal meal);

    LiveData<List<Meal>> getLocalFavouriteMeals();
    LiveData<List<Meal>> getLocalPlannedMeals();
    LiveData<List<Meal>> getLocalFavOrPlannedMeals();
    void updateFavStatus(String id, boolean isFav);
    void updatePlannedStatus(String id, boolean isPlanned);
    LiveData<List<Meal>> getPlannedMealsByDate(String date);


    void addFavoriteMealFB(Meal meal, IFirestoreCallback.IOperationCallback callback);
    void addPlannedMealFB(Meal meal, IFirestoreCallback.IOperationCallback callback);
    ListenerRegistration syncFavoriteMeals(String userId, IFirestoreCallback.ILoadMealsCallback callback);
    ListenerRegistration syncPlannedMeals(String userId, IFirestoreCallback.ILoadMealsCallback callback);

    void removeFavoriteMeal(String userId, String mealId, IFirestoreCallback.IOperationCallback callback);
    void removePlannedMeal(String userId, String mealId, IFirestoreCallback.IOperationCallback callback);
}
