package com.ities45.mealplanner.model.remote.ingredients;

import com.ities45.mealplanner.model.pojo.IngredientResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface I_IngredientService {
    // List all ingredients
    @GET("list.php?i=list")
    Call<IngredientResponse> listAllIngredients();
}
