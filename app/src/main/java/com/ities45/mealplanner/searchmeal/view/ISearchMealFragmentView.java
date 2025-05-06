package com.ities45.mealplanner.searchmeal.view;

import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public interface ISearchMealFragmentView {
    void showMeals(List<Meal> meals);
    void showErrMsg(String errMsg);
    void setTitle(String title);
}
