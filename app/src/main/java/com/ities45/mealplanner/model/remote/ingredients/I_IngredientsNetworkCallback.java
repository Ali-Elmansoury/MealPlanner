package com.ities45.mealplanner.model.remote.ingredients;

import com.ities45.mealplanner.model.pojo.Ingredient;

import java.util.List;

public interface I_IngredientsNetworkCallback {
    void onGetIngredientsSuccess(List<Ingredient> ingredients);
    void onGetIngredientsFailure(String errMsg);
}
