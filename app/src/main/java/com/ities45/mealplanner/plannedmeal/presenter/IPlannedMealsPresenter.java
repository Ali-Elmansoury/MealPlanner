package com.ities45.mealplanner.plannedmeal.presenter;

import androidx.lifecycle.LiveData;

import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public interface IPlannedMealsPresenter {
    LiveData<List<Meal>> getPlannedMealsByDate(String date);
    void removeMealFromPlanned(Meal meal);
    void onPlannedMealClicked(Meal meal);
}
