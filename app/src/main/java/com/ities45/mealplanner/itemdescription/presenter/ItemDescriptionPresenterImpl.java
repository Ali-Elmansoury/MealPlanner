package com.ities45.mealplanner.itemdescription.presenter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.ities45.mealplanner.itemdescription.view.I_ItemDescriptionFragmentView;
import com.ities45.mealplanner.model.pojo.IngredientMeasureItem;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.meals.IMealsNetworkCallback;
import com.ities45.mealplanner.model.repository.meals.IMealsRepository;

import java.util.List;

public class ItemDescriptionPresenterImpl implements I_ItemDescriptionPresenter {
    private I_ItemDescriptionFragmentView view;
    private IMealsRepository repo;

    public ItemDescriptionPresenterImpl(I_ItemDescriptionFragmentView view, IMealsRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void processReceivedMeal(Meal meal) {
        view.showMeal(meal);
    }

    @Override
    public void processReceivedMealId(String id) {
        repo.getMealDetailsById(id, new IMealsNetworkCallback() {
            @Override
            public void onGetMealsSuccess(List<Meal> meals) {
                view.showMeal(meals.get(0));
            }

            @Override
            public void onGetMealsFailure(String errMsg) {
                view.showErrMsg(errMsg);
            }
        });
    }

    @Override
    public List<IngredientMeasureItem> getMealIngredients(Meal meal) {
        return IngredientMeasureItem.extractFromMeal(meal);
    }

    @Override
    public void addMealToFav(Meal meal) {
        meal.setFav(true);
        repo.insertLocalMeal(meal);
        //set bool is fav
    }

    @Override
    public void addMealToPlanned(Meal meal) {
        meal.setPlanned(true);
        repo.insertLocalMeal(meal);
        //set bool is planned
    }

    public void checkConnectionAndUpdateUI() {
        if (repo.isNetworkAvailable()) {
            view.showMainContent();
        } else {
            view.showNoInternetLayout();
            view.showErrMsg("No internet");
        }
    }

    @Override
    public void onResume() {
        repo.registerNetworkCallback();
        checkConnectionAndUpdateUI();
    }

    @Override
    public void onPause() {
        repo.unregisterNetworkCallback();
    }
}
