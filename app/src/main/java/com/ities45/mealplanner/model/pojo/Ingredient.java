package com.ities45.mealplanner.model.pojo;

public class Ingredient {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strType;
    private String ingredientUrl;

    public String getIngredientUrl() {
        ingredientUrl = "https://www.themealdb.com/images/ingredients/" + strIngredient.toLowerCase() + "-small.png";
        return ingredientUrl;
    }

    public void setIngredientUrl(String ingredientUrl) {
        this.ingredientUrl = ingredientUrl;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }
}
