package com.ities45.mealplanner.searchmeal.presenter;

import com.ities45.mealplanner.mainactivity.view.ISearchMealCommunicator;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.meals.IMealsNetworkCallback;
import com.ities45.mealplanner.model.repository.meals.IMealsRepository;
import com.ities45.mealplanner.searchmeal.view.ISearchMealFragmentView;

import java.util.List;

public class SearchMealPresenterImpl implements ISearchMealPresenter{
    private ISearchMealFragmentView view;
    private IMealsRepository repo;
    private ISearchMealCommunicator communicator;

    public SearchMealPresenterImpl(ISearchMealFragmentView view, IMealsRepository repo, ISearchMealCommunicator communicator) {
        this.view = view;
        this.repo = repo;
        this.communicator = communicator;
    }

    @Override
    public void onMealClicked(Meal meal) {
        communicator.sendMealIdToItemDescriptionFragment(meal.getIdMeal());
    }

//    @Override
//    public void searchMealByName(String name) {
//
//    }

    @Override
    public void processReceivedMealItem(String itemName, String itemType) {
        switch (itemType){
            case"category":
                view.setTitle(itemName);
                repo.getMealsByCategory(itemName, new IMealsNetworkCallback() {
                    @Override
                    public void onGetMealsSuccess(List<Meal> meals) {
                        view.showMeals(meals);
                    }

                    @Override
                    public void onGetMealsFailure(String errMsg) {
                        view.showErrMsg(errMsg);
                    }
                });
                break;
            case"ingredient":
                view.setTitle(itemName);
                repo.getMealsByIngredient(itemName, new IMealsNetworkCallback() {
                    @Override
                    public void onGetMealsSuccess(List<Meal> meals) {
                        view.showMeals(meals);
                    }

                    @Override
                    public void onGetMealsFailure(String errMsg) {
                        view.showErrMsg(errMsg);
                    }
                });
                break;
            case"area":
                view.setTitle(itemName);
                repo.getMealsByArea(itemName, new IMealsNetworkCallback() {
                    @Override
                    public void onGetMealsSuccess(List<Meal> meals) {
                        view.showMeals(meals);
                    }

                    @Override
                    public void onGetMealsFailure(String errMsg) {
                        view.showErrMsg(errMsg);
                    }
                });
                break;
            default:
                view.showErrMsg("Error item type is incorrect");
        }
    }

}
