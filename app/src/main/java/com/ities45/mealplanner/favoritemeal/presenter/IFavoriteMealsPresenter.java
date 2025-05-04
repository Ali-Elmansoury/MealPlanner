package com.ities45.mealplanner.favoritemeal.presenter;

import androidx.lifecycle.LiveData;

import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public interface IFavoriteMealsPresenter {
    LiveData<List<Meal>> getFavMeals();
    void removeMealFromFav(Meal meal);
    void onFavMealClicked(Meal meal);

}
