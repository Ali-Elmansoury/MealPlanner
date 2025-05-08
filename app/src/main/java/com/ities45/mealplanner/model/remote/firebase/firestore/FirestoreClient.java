package com.ities45.mealplanner.model.remote.firebase.firestore;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.ities45.mealplanner.model.pojo.Meal;

import java.util.ArrayList;
import java.util.List;

public class FirestoreClient {
    private final FirebaseFirestore db;
    private static FirestoreClient client = null;

    private FirestoreClient() {
        db = FirebaseFirestore.getInstance();
    }

    public static FirestoreClient getInstance(){
        if (client == null){
            client = new FirestoreClient();
        }
        return client;
    }

    public void addFavoriteMeal(String userId, Meal meal, IFirestoreCallback.IOperationCallback callback) {
        addMealToCollection(userId, meal, "favorite_meals", callback);
    }

    public void addPlannedMeal(String userId, Meal meal, IFirestoreCallback.IOperationCallback callback) {
        addMealToCollection(userId, meal, "planned_meals", callback);
    }

    private void addMealToCollection(String userId, Meal meal, String collectionPath,
                                     IFirestoreCallback.IOperationCallback callback) {
        db.collection("users").document(userId).collection(collectionPath)
                .document(meal.getIdMeal())
                .set(meal)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void removeFavoriteMeal(String userId, String mealId, IFirestoreCallback.IOperationCallback callback) {
        removeMealFromCollection(userId, mealId, "favorite_meals", callback);
    }

    public void removePlannedMeal(String userId, String mealId, IFirestoreCallback.IOperationCallback callback) {
        removeMealFromCollection(userId, mealId, "planned_meals", callback);
    }

    private void removeMealFromCollection(String userId, String mealId, String collectionPath,
                                          IFirestoreCallback.IOperationCallback callback) {
        db.collection("users").document(userId).collection(collectionPath)
                .document(mealId)
                .delete()
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public ListenerRegistration syncFavoriteMeals(String userId, IFirestoreCallback.ILoadMealsCallback callback) {
        return syncMealCollection(userId, "favorite_meals", IFirestoreCallback.MealType.FAVORITE, callback);
    }

    public ListenerRegistration syncPlannedMeals(String userId, IFirestoreCallback.ILoadMealsCallback callback) {
        return syncMealCollection(userId, "planned_meals", IFirestoreCallback.MealType.PLANNED, callback);
    }

    private ListenerRegistration syncMealCollection(String userId, String collectionPath,
                                                    IFirestoreCallback.MealType mealType, IFirestoreCallback.ILoadMealsCallback callback) {
        return db.collection("users").document(userId).collection(collectionPath)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        callback.onDataNotAvailable(mealType);
                        return;
                    }

                    List<Meal> meals = new ArrayList<>();
                    if (value != null) {
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            Meal meal = doc.toObject(Meal.class);
                            if (meal != null) meals.add(meal);
                        }
                    }
                    callback.onMealsLoaded(meals, mealType);
                });
    }
}