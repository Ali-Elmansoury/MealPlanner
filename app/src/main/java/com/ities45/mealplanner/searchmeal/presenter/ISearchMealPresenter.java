package com.ities45.mealplanner.searchmeal.presenter;

import com.ities45.mealplanner.model.pojo.Meal;

public interface ISearchMealPresenter {
    void onMealClicked(Meal meal);

    //void searchMealByName(String name);

    void processReceivedMealItem(String itemName, String itemType);
}
