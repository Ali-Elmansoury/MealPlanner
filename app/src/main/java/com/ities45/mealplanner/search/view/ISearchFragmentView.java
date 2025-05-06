package com.ities45.mealplanner.search.view;

import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.Category;
import com.ities45.mealplanner.model.pojo.Ingredient;

import java.util.List;

public interface ISearchFragmentView {
    void showCategories(List<Category> categories);
    void showAreas(List<Area>areas);
    void showIngredients(List<Ingredient> ingredients);
    void showErrMsg(String errorMsg);
}
