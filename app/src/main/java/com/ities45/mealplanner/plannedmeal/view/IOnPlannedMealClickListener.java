package com.ities45.mealplanner.plannedmeal.view;

import com.ities45.mealplanner.model.pojo.Meal;

public interface IOnPlannedMealClickListener {
    void onPlannedMealClick(Meal meal);
    void onPlannedMealRemoveBtnClick(Meal meal);
}
