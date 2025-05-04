package com.ities45.mealplanner.mainactivity.view;

import com.ities45.mealplanner.model.pojo.Meal;

public interface IFavoriteMealCommunicator {
    void sendMealToItemDescriptionFragment(Meal meal);
}
