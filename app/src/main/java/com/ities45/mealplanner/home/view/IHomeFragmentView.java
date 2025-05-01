package com.ities45.mealplanner.home.view;

import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.Category;
import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public interface IHomeFragmentView {
    void showMealOfTheDay(Meal meal);
    void showPopularMeals(List<Meal> meals);
    void showCategories(List<Category>categories);
    void showAreas(List<Area>areas);
    void showErrMsg(String errorMsg);
}
