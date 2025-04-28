package com.ities45.mealplanner.model.remote.categories;

import com.ities45.mealplanner.model.pojo.Category;

import java.util.List;

public interface ICategoriesNetworkCallback {
    void onGetCategoriesSuccess(List<Category> categories);
    void onGetCategoriesFailure(String errMsg);
}
