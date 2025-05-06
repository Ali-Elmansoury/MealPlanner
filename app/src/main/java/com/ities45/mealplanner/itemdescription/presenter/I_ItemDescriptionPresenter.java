package com.ities45.mealplanner.itemdescription.presenter;

import com.ities45.mealplanner.model.pojo.IngredientMeasureItem;
import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;
import java.util.function.Consumer;

public interface I_ItemDescriptionPresenter {
    void processReceivedMeal(Meal meal);

    void processReceivedMealId(String id);

    List<IngredientMeasureItem> getMealIngredients(Meal meal);

    void addMealToFav(Meal meal);

    void addMealToPlanned(Meal meal, String date);

    void checkConnectionAndUpdateUI();

    void onResume();

    void onPause();

    void checkFavMeal(Meal fMeal, Consumer<Boolean> callback);
}