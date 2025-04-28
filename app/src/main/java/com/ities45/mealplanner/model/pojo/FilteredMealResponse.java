package com.ities45.mealplanner.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilteredMealResponse {
    @SerializedName("meals")
    private List<FilteredMeal> filteredMeals;

    public List<FilteredMeal> getFilteredMeals() {
        return filteredMeals;
    }

    public void setFilteredMeals(List<FilteredMeal> filteredMeals) {
        this.filteredMeals = filteredMeals;
    }
}
