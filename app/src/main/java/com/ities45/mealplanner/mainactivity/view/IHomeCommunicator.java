package com.ities45.mealplanner.mainactivity.view;

import com.ities45.mealplanner.model.pojo.Meal;

public interface IHomeCommunicator {
    void sendMealToItemDescriptionFragment(Meal meal);
    void sendMealIdToItemDescriptionFragment(String id);
    void onSearchItemClickedSendToSearchMeal(String itemName, String itemType);
}
