package com.ities45.mealplanner.favoritemeal.view;

import com.ities45.mealplanner.model.pojo.Meal;

public interface IOnFavoriteMealClickListener {
    void onFavMealCLick(Meal meal);
    void onFavMealRemoveBtnClick(Meal meal);
}
