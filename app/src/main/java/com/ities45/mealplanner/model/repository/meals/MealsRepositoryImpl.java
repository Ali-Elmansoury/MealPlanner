package com.ities45.mealplanner.model.repository.meals;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;
import com.ities45.mealplanner.model.local.db.IMealsLocalDataSource;
import com.ities45.mealplanner.model.local.networklistener.NetworkManager;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.areas.IAreasNetworkCallback;
import com.ities45.mealplanner.model.remote.areas.IAreasRemoteDataSource;
import com.ities45.mealplanner.model.remote.categories.ICategoriesNetworkCallback;
import com.ities45.mealplanner.model.remote.categories.ICategoriesRemoteDataSource;
import com.ities45.mealplanner.model.remote.firebase.firestore.FirestoreClient;
import com.ities45.mealplanner.model.remote.firebase.firestore.IFirestoreCallback;
import com.ities45.mealplanner.model.remote.ingredients.I_IngredientsNetworkCallback;
import com.ities45.mealplanner.model.remote.ingredients.I_IngredientsRemoteDataSource;
import com.ities45.mealplanner.model.remote.meals.IMealsNetworkCallback;
import com.ities45.mealplanner.model.remote.meals.IMealsRemoteDataSource;

import java.util.List;

public class MealsRepositoryImpl implements IMealsRepository{
    private static MealsRepositoryImpl repo = null;
    private IMealsRemoteDataSource mealsRemoteDataSource;
    private ICategoriesRemoteDataSource categoriesRemoteDataSource;
    private IAreasRemoteDataSource areasRemoteDataSource;
    private I_IngredientsRemoteDataSource ingredientsRemoteDataSource;
    private NetworkManager networkManager;
    private IMealsLocalDataSource mealsLocalDataSource;
    private FirestoreClient firestoreClient;

    private ListenerRegistration favoriteListener;
    private ListenerRegistration plannedListener;

    private MealsRepositoryImpl(IMealsRemoteDataSource mealsRemoteDataSource, ICategoriesRemoteDataSource categoriesRemoteDataSource, IAreasRemoteDataSource areasRemoteDataSource, I_IngredientsRemoteDataSource ingredientsRemoteDataSource, NetworkManager networkManager, IMealsLocalDataSource mealsLocalDataSource, FirestoreClient firestoreClient) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.categoriesRemoteDataSource = categoriesRemoteDataSource;
        this.areasRemoteDataSource = areasRemoteDataSource;
        this.ingredientsRemoteDataSource = ingredientsRemoteDataSource;
        this.networkManager = networkManager;
        this.mealsLocalDataSource = mealsLocalDataSource;
        this.firestoreClient = firestoreClient;
    }

    public static MealsRepositoryImpl getInstance(IMealsRemoteDataSource mealsRemoteDataSource, ICategoriesRemoteDataSource categoriesRemoteDataSource, IAreasRemoteDataSource areasRemoteDataSource, I_IngredientsRemoteDataSource ingredientsRemoteDataSource, NetworkManager networkManager, IMealsLocalDataSource mealsLocalDataSource, FirestoreClient firestoreClient){
        if(repo == null){
            repo = new MealsRepositoryImpl(mealsRemoteDataSource, categoriesRemoteDataSource, areasRemoteDataSource, ingredientsRemoteDataSource, networkManager, mealsLocalDataSource, firestoreClient);
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

    @Override
    public void insertLocalMeal(Meal meal) {
        mealsLocalDataSource.insertLocalMeal(meal);
    }

    @Override
    public void insertLocalAllMeals(List<Meal> meals) {
        mealsLocalDataSource.insertLocalAllMeals(meals);
    }

    @Override
    public LiveData<List<Meal>> getAllLocalStoredMeals() {
        return mealsLocalDataSource.getAllLocalStoredMeals();
    }

    @Override
    public LiveData<Meal> getLocalMealById(String id) {
        return mealsLocalDataSource.getLocalMealById(id);
    }

    @Override
    public void deleteLocalMeal(Meal meal) {
        mealsLocalDataSource.deleteLocalMeal(meal);
    }

    @Override
    public void deleteAllLocalMeals() {
        mealsLocalDataSource.deleteAllLocalMeals();
    }

    @Override
    public void updateLocalMeal(Meal meal) {
        mealsLocalDataSource.updateLocalMeal(meal);
    }

    @Override
    public LiveData<List<Meal>> getLocalFavouriteMeals() {
        return mealsLocalDataSource.getLocalFavouriteMeals();
    }

    @Override
    public LiveData<List<Meal>> getLocalPlannedMeals() {
        return mealsLocalDataSource.getLocalPlannedMeals();
    }

    @Override
    public LiveData<List<Meal>> getLocalFavOrPlannedMeals() {
        return mealsLocalDataSource.getLocalFavOrPlannedMeals();
    }

    @Override
    public void updateFavStatus(String id, boolean isFav) {
        mealsLocalDataSource.updateFavStatus(id, isFav);
    }

    @Override
    public void updatePlannedStatus(String id, boolean isPlanned) {
        mealsLocalDataSource.updatePlannedStatus(id, isPlanned);
    }

    @Override
    public LiveData<List<Meal>> getPlannedMealsByDate(String date) {
        return mealsLocalDataSource.getPlannedMealsByDate(date);
    }

    @Override
    public void addFavoriteMealFB(Meal meal, IFirestoreCallback.IOperationCallback callback) {
        // Get current user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (userId == null) {
            callback.onError("User not authenticated");
            return;
        }

        // Update local database first
        mealsLocalDataSource.insertLocalMeal(meal);

        // Add to Firestore
        firestoreClient.addFavoriteMeal(userId, meal, new IFirestoreCallback.IOperationCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
                // Consider rolling back local change if needed
            }
        });
    }

    @Override
    public void addPlannedMealFB(Meal meal, IFirestoreCallback.IOperationCallback callback) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (userId == null) {
            callback.onError("User not authenticated");
            return;
        }

        mealsLocalDataSource.insertLocalMeal(meal);

        firestoreClient.addPlannedMeal(userId, meal, new IFirestoreCallback.IOperationCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public ListenerRegistration syncFavoriteMeals(String userId, IFirestoreCallback.ILoadMealsCallback callback) {
        removeFavoriteSync();
        favoriteListener = firestoreClient.syncFavoriteMeals(userId,
                new IFirestoreCallback.ILoadMealsCallback() {
                    @Override
                    public void onMealsLoaded(List<Meal> meals, IFirestoreCallback.MealType mealType) {
                        mealsLocalDataSource.syncFavorites(meals);
                        callback.onMealsLoaded(meals, mealType);
                    }

                    @Override
                    public void onDataNotAvailable(IFirestoreCallback.MealType mealType) {
                        callback.onDataNotAvailable(mealType);
                    }
                });
        return favoriteListener;
    }

    @Override
    public ListenerRegistration syncPlannedMeals(String userId, IFirestoreCallback.ILoadMealsCallback callback) {
        removePlannedSync();
        plannedListener = firestoreClient.syncPlannedMeals(userId,
                new IFirestoreCallback.ILoadMealsCallback() {
                    @Override
                    public void onMealsLoaded(List<Meal> meals, IFirestoreCallback.MealType mealType) {
                        mealsLocalDataSource.syncPlanned(meals);
                        callback.onMealsLoaded(meals, mealType);
                    }

                    @Override
                    public void onDataNotAvailable(IFirestoreCallback.MealType mealType) {
                        callback.onDataNotAvailable(mealType);
                    }
                });
        return plannedListener;
    }

    @Override
    public void removeFavoriteMeal(String userId, String mealId, IFirestoreCallback.IOperationCallback callback) {
        if (userId == null) {
            callback.onError("User not authenticated");
            return;
        }
        firestoreClient.removeFavoriteMeal(userId, mealId, callback);
    }

    @Override
    public void removePlannedMeal(String userId, String mealId, IFirestoreCallback.IOperationCallback callback) {
        if (userId == null) {
            callback.onError("User not authenticated");
            return;
        }
        firestoreClient.removePlannedMeal(userId, mealId, callback);
    }

    // Add these cleanup methods
    public void removeFavoriteSync() {
        if (favoriteListener != null) {
            favoriteListener.remove();
            favoriteListener = null;
        }
    }

    public void removePlannedSync() {
        if (plannedListener != null) {
            plannedListener.remove();
            plannedListener = null;
        }
    }

    public void cleanup() {
        removeFavoriteSync();
        removePlannedSync();
    }
}
