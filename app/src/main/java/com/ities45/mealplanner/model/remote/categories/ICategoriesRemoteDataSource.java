package com.ities45.mealplanner.model.remote.categories;

public interface ICategoriesRemoteDataSource {
    void getAllMealCategoriesNetworkCallback(ICategoriesNetworkCallback networkCallback);
    void listAllCategoriesNamesNetworkCallback(ICategoriesNetworkCallback networkCallback);
}
