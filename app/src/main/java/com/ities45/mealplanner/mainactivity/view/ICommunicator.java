package com.ities45.mealplanner.mainactivity.view;

import com.ities45.mealplanner.model.pojo.Meal;

public interface ICommunicator {
    void sendMealToItemDescriptionFragment(Meal meal);
    void sendMealIdToItemDescriptionFragment(String id);
}
