package com.ities45.mealplanner.plannedmeal.presenter;

import androidx.lifecycle.LiveData;

import com.ities45.mealplanner.mainactivity.view.IPlannedMealCommunicator;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.repository.meals.IMealsRepository;
import com.ities45.mealplanner.plannedmeal.view.IPlannedMealsFragmentView;

import java.util.List;

public class PlannedMealsPresenterImpl implements IPlannedMealsPresenter{
    private IPlannedMealsFragmentView view;
    private IMealsRepository repo;
    private IPlannedMealCommunicator communicator;

    public PlannedMealsPresenterImpl(IPlannedMealsFragmentView view, IMealsRepository repo, IPlannedMealCommunicator communicator) {
        this.view = view;
        this.repo = repo;
        this.communicator = communicator;
    }

    @Override
    public LiveData<List<Meal>> getPlannedMealsByDate(String date) {
        return repo.getPlannedMealsByDate(date);
    }

    @Override
    public void removeMealFromPlanned(Meal meal) {
        repo.updatePlannedStatus(meal.getIdMeal(), false);
    }

    @Override
    public void onPlannedMealClicked(Meal meal) {
        communicator.sendMealToItemDescriptionFragment(meal);
    }
}
