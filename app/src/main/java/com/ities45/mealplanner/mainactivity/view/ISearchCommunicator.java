package com.ities45.mealplanner.mainactivity.view;

import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.Category;
import com.ities45.mealplanner.model.pojo.Ingredient;

public interface ISearchCommunicator {
    void sendCategoryToSearchMealFragment(Category category);
    void sendAreaToSearchMealFragment(Area area);
    void sendIngredientToSearchMealFragment(Ingredient ingredient);
}
