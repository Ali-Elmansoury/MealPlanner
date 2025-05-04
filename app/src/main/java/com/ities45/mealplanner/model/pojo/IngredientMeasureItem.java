package com.ities45.mealplanner.model.pojo;

import java.util.ArrayList;
import java.util.List;

public class IngredientMeasureItem {
    private final String name;
    private final String measure;
    private final String thumbnailUrl;

    public IngredientMeasureItem(String name, String measure) {
        this.name = name;
        this.measure = measure;
        this.thumbnailUrl = generateThumbnailUrl(name);
    }

    // Static extraction method
    public static List<IngredientMeasureItem> extractFromMeal(Meal meal) {
        List<IngredientMeasureItem> items = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String ingredient = meal.getStrIngredient(i);
            String measure = meal.getStrMeasure(i);
            if (ingredient != null && !ingredient.trim().isEmpty()) {
                items.add(new IngredientMeasureItem(ingredient, measure));
            }
        }
        return items;
    }

    private String generateThumbnailUrl(String ingredientName) {
        if (ingredientName == null) return "";
        String formatted = ingredientName.replace(" ", "%20");
        return "https://www.themealdb.com/images/ingredients/" + formatted + "-small.png";
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}