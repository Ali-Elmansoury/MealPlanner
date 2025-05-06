package com.ities45.mealplanner.search.presenter;

import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.Category;
import com.ities45.mealplanner.model.pojo.Ingredient;

public interface ISearchPresenter {
    void getCategories();
    void getAreas();
    void getIngredients();
    void onCategoryClicked(Category category);
    void onIngredientClicked(Ingredient ingredient);
    void onAreaClicked(Area area);
}
