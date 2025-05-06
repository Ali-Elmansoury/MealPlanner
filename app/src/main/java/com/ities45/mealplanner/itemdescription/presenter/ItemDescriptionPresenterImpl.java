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
import java.util.function.Consumer;

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
    public void addMealToFav(Meal fmeal) {
        LiveData<Meal> favMeal = repo.getLocalMealById(fmeal.getIdMeal());
        favMeal.observeForever(new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                if (meal != null && Boolean.TRUE.equals(meal.getFav())) {
                    view.favMealExists();
                }
                // Update or insert
                else if (meal != null) {
                    repo.updateFavStatus(fmeal.getIdMeal(), true);
                    view.favMealAdded();
                } else {
                    fmeal.setFav(true);
                    repo.insertLocalMeal(fmeal);
                    view.favMealAdded();
                }
                favMeal.removeObserver(this);
            }
        });
    }

    @Override
    public void addMealToPlanned(Meal pmeal, String date) {
        LiveData<Meal> plannedMeal = repo.getLocalMealById(pmeal.getIdMeal());
        plannedMeal.observeForever(new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                if (meal != null && Boolean.TRUE.equals(meal.getPlanned())) {
                    view.plannedMealExists();
                }
                // Update or insert
                else if (meal != null) {
                    repo.updatePlannedStatus(pmeal.getIdMeal(), true);
                    view.plannedMealAdded();
                } else {
                    pmeal.setPlanned(true);
                    pmeal.setPlannedDate(date);
                    repo.insertLocalMeal(pmeal);
                    view.plannedMealAdded();
                }
                plannedMeal.removeObserver(this);
            }
        });
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

    @Override
    public void checkFavMeal(Meal fMeal, Consumer<Boolean> callback) {
        LiveData<Meal> favMeal = repo.getLocalMealById(fMeal.getIdMeal());
        favMeal.observeForever(new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                boolean isFav = meal != null && Boolean.TRUE.equals(meal.getFav());
                callback.accept(isFav);
                favMeal.removeObserver(this); // Ensure observer is removed
            }
        });
    }
}
