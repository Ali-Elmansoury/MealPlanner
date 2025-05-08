package com.ities45.mealplanner.plannedmeal.view;

import com.ities45.mealplanner.model.pojo.Meal;

public interface IPlannedMealsFragmentView {
    void showPlannedMeal(Meal meal);
    void showErrMsg(String errMsg);
    void showFBStatus(String msg);
}
