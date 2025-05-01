package com.ities45.mealplanner.model.pojo;

import java.util.List;

public class MealResponse {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        // Map the strArea (country name) to a flag URL
//        for (Meal meal : meals) {
//            String areaName = meal.getStrArea();
//            String areaCode = AreaMapper.getAreaCode(areaName); // Get the country code
//            String flagUrl = "https://flagcdn.com/64x48/" + areaCode + ".png"; // Flag URL, https://flagcdn.com/{size}/{country_code}.png
//            meal.setFlagUrl(flagUrl); // Set the flag URL in the meal object
//        }
    }
}

