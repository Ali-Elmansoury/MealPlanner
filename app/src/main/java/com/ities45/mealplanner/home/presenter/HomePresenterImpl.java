package com.ities45.mealplanner.home.presenter;

import com.ities45.mealplanner.home.view.IHomeFragmentView;
import com.ities45.mealplanner.mainactivity.view.IHomeCommunicator;
import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.Category;
import com.ities45.mealplanner.model.pojo.Ingredient;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.areas.IAreasNetworkCallback;
import com.ities45.mealplanner.model.remote.categories.ICategoriesNetworkCallback;
import com.ities45.mealplanner.model.remote.ingredients.I_IngredientsNetworkCallback;
import com.ities45.mealplanner.model.remote.meals.IMealsNetworkCallback;
import com.ities45.mealplanner.model.repository.meals.IMealsRepository;

import java.util.List;

public class HomePresenterImpl implements IHomePresenter{
    private IHomeFragmentView view;
    private IMealsRepository repo;
    private IHomeCommunicator communicator;

    public HomePresenterImpl(IHomeFragmentView view, IMealsRepository repo, IHomeCommunicator communicator) {
        this.view = view;
        this.repo = repo;
        this.communicator = communicator;
    }

    @Override
    public void getMealOfTheDay() {
        repo.getRandomMeal(new IMealsNetworkCallback() {
            @Override
            public void onGetMealsSuccess(List<Meal> meals) {
                view.showMealOfTheDay(meals.get(0));
            }

            @Override
            public void onGetMealsFailure(String errMsg) {
                view.showErrMsg(errMsg);
            }
        });
    }

    @Override
    public void getPopularMeals() {
        repo.getMealsByCategory("Breakfast", new IMealsNetworkCallback() {
            @Override
            public void onGetMealsSuccess(List<Meal> meals) {
                view.showPopularMeals(meals);
            }

            @Override
            public void onGetMealsFailure(String errMsg) {
                view.showErrMsg(errMsg);
            }
        });
    }

    @Override
    public void getCategories() {
        repo.getAllMealCategories(new ICategoriesNetworkCallback() {
            @Override
            public void onGetCategoriesSuccess(List<Category> categories) {
                view.showCategories(categories);
            }

            @Override
            public void onGetCategoriesFailure(String errMsg) {
                view.showErrMsg(errMsg);
            }
        });
    }

    @Override
    public void getAreas() {
        repo.listAllAreasNames(new IAreasNetworkCallback() {
            @Override
            public void onGetAreasSuccess(List<Area> areas) {
                view.showAreas(areas);
            }

            @Override
            public void onGetAreasFailure(String errMsg) {
                view.showErrMsg(errMsg);
            }
        });
    }

    @Override
    public void getIngredients() {
        repo.listAllIngredients(new I_IngredientsNetworkCallback() {
            @Override
            public void onGetIngredientsSuccess(List<Ingredient> ingredients) {
                view.showIngredients(ingredients);
            }

            @Override
            public void onGetIngredientsFailure(String errMsg) {
                view.showErrMsg(errMsg);
            }
        });
    }

    @Override
    public void MagicMealRoulette() {
        repo.getRandomMeal(new IMealsNetworkCallback() {
            @Override
            public void onGetMealsSuccess(List<Meal> meals) {
                view.showMealOfTheDay(meals.get(0));
            }

            @Override
            public void onGetMealsFailure(String errMsg) {
                view.showErrMsg(errMsg);
            }
        });
    }

    @Override
    public void checkConnectionAndUpdateUI() {
        if (repo.isNetworkAvailable()) {
            view.showMainContent();
            getMealOfTheDay();
            getPopularMeals();
            getCategories();
            getAreas();
            getIngredients();
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
    public void onMealClicked(Meal meal){
        communicator.sendMealToItemDescriptionFragment(meal);
    }

    @Override
    public void onMealIdClicked(String id) {
        communicator.sendMealIdToItemDescriptionFragment(id);
    }

    @Override
    public void onCIAItemClicked(String itemName, String itemType) {
        communicator.onSearchItemClickedSendToSearchMeal(itemName, itemType);
    }
}
