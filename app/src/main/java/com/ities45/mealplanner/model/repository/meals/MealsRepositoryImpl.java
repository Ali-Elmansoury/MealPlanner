package com.ities45.mealplanner.model.repository.meals;

import com.ities45.mealplanner.model.local.networklistener.NetworkManager;
import com.ities45.mealplanner.model.remote.areas.IAreasNetworkCallback;
import com.ities45.mealplanner.model.remote.areas.IAreasRemoteDataSource;
import com.ities45.mealplanner.model.remote.categories.ICategoriesNetworkCallback;
import com.ities45.mealplanner.model.remote.categories.ICategoriesRemoteDataSource;
import com.ities45.mealplanner.model.remote.ingredients.I_IngredientsNetworkCallback;
import com.ities45.mealplanner.model.remote.ingredients.I_IngredientsRemoteDataSource;
import com.ities45.mealplanner.model.remote.meals.IMealsNetworkCallback;
import com.ities45.mealplanner.model.remote.meals.IMealsRemoteDataSource;

public class MealsRepositoryImpl implements IMealsRepository{
    private static MealsRepositoryImpl repo = null;
    private IMealsRemoteDataSource mealsRemoteDataSource;
    private ICategoriesRemoteDataSource categoriesRemoteDataSource;
    private IAreasRemoteDataSource areasRemoteDataSource;
    private I_IngredientsRemoteDataSource ingredientsRemoteDataSource;
    private NetworkManager networkManager;

    private MealsRepositoryImpl(IMealsRemoteDataSource mealsRemoteDataSource, ICategoriesRemoteDataSource categoriesRemoteDataSource, IAreasRemoteDataSource areasRemoteDataSource, I_IngredientsRemoteDataSource ingredientsRemoteDataSource, NetworkManager networkManager) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.categoriesRemoteDataSource = categoriesRemoteDataSource;
        this.areasRemoteDataSource = areasRemoteDataSource;
        this.ingredientsRemoteDataSource = ingredientsRemoteDataSource;
        this.networkManager = networkManager;
    }

    public static MealsRepositoryImpl getInstance(IMealsRemoteDataSource mealsRemoteDataSource, ICategoriesRemoteDataSource categoriesRemoteDataSource, IAreasRemoteDataSource areasRemoteDataSource, I_IngredientsRemoteDataSource ingredientsRemoteDataSource, NetworkManager networkManager){
        if(repo == null){
            repo = new MealsRepositoryImpl(mealsRemoteDataSource, categoriesRemoteDataSource, areasRemoteDataSource, ingredientsRemoteDataSource, networkManager);
        }
        return repo;
    }


    @Override
    public void getMealsByCategory(String category, IMealsNetworkCallback networkCallback) {
        mealsRemoteDataSource.getMealsByCategoryNetworkCall(category, networkCallback);
    }

    @Override
    public void getMealsByArea(String area, IMealsNetworkCallback networkCallback) {
        mealsRemoteDataSource.getMealsByAreaNetworkCall(area, networkCallback);
    }

    @Override
    public void getMealsByIngredient(String ingredient, IMealsNetworkCallback networkCallback) {
        mealsRemoteDataSource.getMealsByIngredientNetworkCall(ingredient, networkCallback);
    }

    @Override
    public void searchMealsByName(String mealName, IMealsNetworkCallback networkCallback) {
        mealsRemoteDataSource.searchMealsByNameNetworkCall(mealName, networkCallback);
    }

    @Override
    public void searchMealsByFirstLetter(String firstLetter, IMealsNetworkCallback networkCallback) {
        mealsRemoteDataSource.searchMealsByFirstLetterNetworkCall(firstLetter, networkCallback);
    }

    @Override
    public void getMealDetailsById(String mealId, IMealsNetworkCallback networkCallback) {
        mealsRemoteDataSource.getMealDetailsByIdNetworkCall(mealId, networkCallback);
    }

    @Override
    public void getRandomMeal(IMealsNetworkCallback networkCallback) {
        mealsRemoteDataSource.getRandomMealNetworkCall(networkCallback);
    }

    @Override
    public void listAllAreasNames(IAreasNetworkCallback networkCallback) {
        areasRemoteDataSource.listAllAreasNamesNetworkCallback(networkCallback);
    }

    @Override
    public void listAllIngredients(I_IngredientsNetworkCallback networkCallback) {
        ingredientsRemoteDataSource.listAllIngredientsNetworkCallback(networkCallback);
    }

    @Override
    public void getAllMealCategories(ICategoriesNetworkCallback networkCallback) {
        categoriesRemoteDataSource.getAllMealCategoriesNetworkCallback(networkCallback);
    }

    @Override
    public void listAllCategoriesNames(ICategoriesNetworkCallback networkCallback) {
        categoriesRemoteDataSource.listAllCategoriesNamesNetworkCallback(networkCallback);
    }

    @Override
    public boolean isNetworkAvailable() {
        return networkManager.isNetworkAvailable();
    }

    @Override
    public void registerNetworkCallback() {
        networkManager.registerNetworkCallback();
    }

    @Override
    public void unregisterNetworkCallback() {
        networkManager.unregisterNetworkCallback();
    }
}
