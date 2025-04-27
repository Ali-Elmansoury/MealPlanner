package com.ities45.mealplanner.model.pojo;

import java.util.List;

public class FilteredMealResponse {
    private List<FilteredMeal> filteredMeals;

    public List<FilteredMeal> getMeals() {
        return filteredMeals;
    }

    public void setMeals(List<FilteredMeal> filteredMeals) {
        this.filteredMeals = filteredMeals;
    }
}
