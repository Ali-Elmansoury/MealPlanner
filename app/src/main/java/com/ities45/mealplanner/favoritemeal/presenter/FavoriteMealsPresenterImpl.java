package com.ities45.mealplanner.favoritemeal.presenter;

import androidx.lifecycle.LiveData;

import com.ities45.mealplanner.favoritemeal.view.IFavoriteMealsFragmentView;
import com.ities45.mealplanner.mainactivity.view.IFavoriteMealCommunicator;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.repository.meals.IMealsRepository;

import java.util.List;

public class FavoriteMealsPresenterImpl implements IFavoriteMealsPresenter{
    private IFavoriteMealsFragmentView view;
    private IMealsRepository repo;
    private IFavoriteMealCommunicator communicator;

    public FavoriteMealsPresenterImpl(IFavoriteMealsFragmentView view, IMealsRepository repo, IFavoriteMealCommunicator communicator) {
        this.view = view;
        this.repo = repo;
        this.communicator = communicator;
    }

    @Override
    public LiveData<List<Meal>> getFavMeals() {
        return repo.getLocalFavouriteMeals();
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        repo.updateFavStatus(meal.getIdMeal(), false);
    }

    @Override
    public void onFavMealClicked(Meal meal) {
        communicator.sendMealToItemDescriptionFragment(meal);
    }
}
