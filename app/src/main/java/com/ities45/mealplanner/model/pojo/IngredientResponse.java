package com.ities45.mealplanner.model.pojo;

import java.util.List;

public class IngredientResponse {
    private List<Ingredient> ingredients;

    public List<Ingredient> getMeals() {
        return ingredients;
    }

    public void setMeals(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
