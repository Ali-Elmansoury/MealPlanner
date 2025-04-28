package com.ities45.mealplanner.model.remote.meals;

import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public interface IMealsNetworkCallback {
    void onGetMealsSuccess(List<Meal> meals);
    void onGetMealsFailure(String errMsg);
}
