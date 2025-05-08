package com.ities45.mealplanner.itemdescription.view;

import com.ities45.mealplanner.model.pojo.Meal;

public interface I_ItemDescriptionFragmentView {
    void showMeal(Meal meal);

    void showErrMsg(String errorMsg);
    void showMainContent();
    void showNoInternetLayout();

    void favMealExists();
    void favMealAdded();

    void plannedMealAdded();
    void plannedMealExists();

    void fbMealAddFailed(String msg);
    void fbMealAdded();
}
