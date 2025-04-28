package com.ities45.mealplanner.model.remote.categories;

import com.ities45.mealplanner.model.pojo.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICategoryService {
    // Get categories
    @GET("categories.php")
    Call<CategoryResponse> getAllMealCategories();

    // List all categories (names only)
    @GET("list.php?c=list")
    Call<CategoryResponse> listAllCategoriesNames();
}
