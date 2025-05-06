package com.ities45.mealplanner.search.presenter;

import com.ities45.mealplanner.mainactivity.view.ISearchCommunicator;
import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.Category;
import com.ities45.mealplanner.model.pojo.Ingredient;
import com.ities45.mealplanner.model.remote.areas.IAreasNetworkCallback;
import com.ities45.mealplanner.model.remote.categories.ICategoriesNetworkCallback;
import com.ities45.mealplanner.model.remote.ingredients.I_IngredientsNetworkCallback;
import com.ities45.mealplanner.model.repository.meals.IMealsRepository;
import com.ities45.mealplanner.search.view.ISearchFragmentView;

import java.util.List;

public class SearchPresenterImpl implements ISearchPresenter{
    private ISearchFragmentView view;
    private IMealsRepository repo;
    private ISearchCommunicator communicator;

    public SearchPresenterImpl(ISearchFragmentView view, IMealsRepository repo, ISearchCommunicator communicator) {
        this.view = view;
        this.repo = repo;
        this.communicator = communicator;
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
    public void onCategoryClicked(Category category) {
        communicator.sendCategoryToSearchMealFragment(category);
    }

    @Override
    public void onIngredientClicked(Ingredient ingredient) {
        communicator.sendIngredientToSearchMealFragment(ingredient);
    }

    @Override
    public void onAreaClicked(Area area) {
        communicator.sendAreaToSearchMealFragment(area);
    }
}
