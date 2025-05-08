package com.ities45.mealplanner.plannedmeal.presenter;

import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.ities45.mealplanner.mainactivity.view.IPlannedMealCommunicator;
import com.ities45.mealplanner.model.local.sessionmanager.SessionManager;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.firebase.firestore.IFirestoreCallback;
import com.ities45.mealplanner.model.repository.meals.IMealsRepository;
import com.ities45.mealplanner.plannedmeal.view.IPlannedMealsFragmentView;

import java.util.List;

public class PlannedMealsPresenterImpl implements IPlannedMealsPresenter{
    private IPlannedMealsFragmentView view;
    private IMealsRepository repo;
    private IPlannedMealCommunicator communicator;
    private SessionManager sessionManager;

    public PlannedMealsPresenterImpl(IPlannedMealsFragmentView view, IMealsRepository repo, IPlannedMealCommunicator communicator, SessionManager sessionManager) {
        this.view = view;
        this.repo = repo;
        this.communicator = communicator;
        this.sessionManager = sessionManager;
    }

    @Override
    public LiveData<List<Meal>> getPlannedMealsByDate(String date) {
        return repo.getPlannedMealsByDate(date);
    }

    @Override
    public void removeMealFromPlanned(Meal meal) {
        repo.updatePlannedStatus(meal.getIdMeal(), false);
        repo.removePlannedMeal(sessionManager.getUserId(), meal.getIdMeal(), new IFirestoreCallback.IOperationCallback() {
            @Override
            public void onSuccess() {
                if (view != null) {
                    view.showFBStatus("planned meal removed from firestore");
                }
                //view.showFBStatus("planned meal removed from firestore");
            }

            @Override
            public void onError(String error) {
                if (view != null) {
                    view.showErrMsg(error);
                }
                //view.showErrMsg(error);
            }
        });

    }

    @Override
    public void onPlannedMealClicked(Meal meal) {
        communicator.sendMealToItemDescriptionFragment(meal);
    }
}
