package com.ities45.mealplanner.model.remote.firebase.firestore;

import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public interface IFirestoreCallback {
    interface IOperationCallback {
        void onSuccess();
        void onError(String error);
    }

    interface ILoadMealsCallback {
        void onMealsLoaded(List<Meal> meals, MealType mealType);
        void onDataNotAvailable(MealType mealType);
    }

    enum MealType {
        FAVORITE,
        PLANNED
    }
}
