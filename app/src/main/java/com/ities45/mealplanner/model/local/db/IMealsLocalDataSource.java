package com.ities45.mealplanner.model.local.db;

import androidx.lifecycle.LiveData;

import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public interface IMealsLocalDataSource {
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


}
