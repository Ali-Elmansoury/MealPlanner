package com.ities45.mealplanner.favoritemeal.presenter;

import androidx.lifecycle.LiveData;

import com.ities45.mealplanner.favoritemeal.view.IFavoriteMealsFragmentView;
import com.ities45.mealplanner.mainactivity.view.IFavoriteMealCommunicator;
import com.ities45.mealplanner.model.local.sessionmanager.SessionManager;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.firebase.firestore.IFirestoreCallback;
import com.ities45.mealplanner.model.repository.meals.IMealsRepository;

import java.util.List;

public class FavoriteMealsPresenterImpl implements IFavoriteMealsPresenter{
    private IFavoriteMealsFragmentView view;
    private IMealsRepository repo;
    private IFavoriteMealCommunicator communicator;
    private SessionManager sessionManager;

    public FavoriteMealsPresenterImpl(IFavoriteMealsFragmentView view, IMealsRepository repo, IFavoriteMealCommunicator communicator, SessionManager sessionManager) {
        this.view = view;
        this.repo = repo;
        this.communicator = communicator;
        this.sessionManager = sessionManager;
    }

    @Override
    public LiveData<List<Meal>> getFavMeals() {
        return repo.getLocalFavouriteMeals();
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        repo.updateFavStatus(meal.getIdMeal(), false);
        repo.removeFavoriteMeal(sessionManager.getUserId(), meal.getIdMeal(), new IFirestoreCallback.IOperationCallback() {
            @Override
            public void onSuccess() {
                //view.showFBStatus("fav meal removed from firestore");
                if (view != null) {
                    view.showFBStatus("fav meal removed from firestore");
                }
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
    public void onFavMealClicked(Meal meal) {
        communicator.sendMealToItemDescriptionFragment(meal);
    }
}
