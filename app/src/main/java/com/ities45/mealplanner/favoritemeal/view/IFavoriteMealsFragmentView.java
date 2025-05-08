package com.ities45.mealplanner.favoritemeal.view;

import com.ities45.mealplanner.model.pojo.Meal;

public interface IFavoriteMealsFragmentView {
    void showFavMeal(Meal meal);
    void showErrMsg(String errMsg);
    void showFBStatus(String msg);

}
