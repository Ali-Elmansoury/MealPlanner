package com.ities45.mealplanner.home.presenter;

import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.firebase.firestore.IFirestoreCallback;

public interface IHomePresenter {
    void getMealOfTheDay();
    void getPopularMeals();
    void getCategories();
    void getAreas();
    void getIngredients();
    void MagicMealRoulette();
    void checkConnectionAndUpdateUI();
    void onResume();
    void onPause();

    void onMealClicked(Meal meal);
    void onMealIdClicked(String id);

    void onCIAItemClicked(String itemName, String itemType);

    void syncFavoriteMeals(String userId, IFirestoreCallback.ILoadMealsCallback callback);

    void syncPlannedMeals(String userId, IFirestoreCallback.ILoadMealsCallback callback);

}
