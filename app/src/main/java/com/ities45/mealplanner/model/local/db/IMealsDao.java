package com.ities45.mealplanner.model.local.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

@Dao
public interface IMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Meal> meals);

    @Query("SELECT * FROM meals")
    LiveData<List<Meal>> getAllMeals();

    @Query("SELECT * FROM meals WHERE idMeal = :id")
    LiveData<Meal> getMealById(String id);

    @Delete
    void deleteMeal(Meal meal);

    @Update
    void updateMeal(Meal meal);

    @Query("DELETE FROM meals")
    void deleteAllMeals();

    // Query all meals where isFav = true
    @Query("SELECT * FROM meals WHERE isFav = 1")
    LiveData<List<Meal>> getFavouriteMeals();

    // Query all meals where isPlanned = true
    @Query("SELECT * FROM meals WHERE isPlanned = 1")
    LiveData<List<Meal>> getPlannedMeals();

    // Query meals where either isFav or isPlanned is true
    @Query("SELECT * FROM meals WHERE isFav = 1 OR isPlanned = 1")
    LiveData<List<Meal>> getFavOrPlannedMeals();

    // Update isFav status for a meal by ID
    @Query("UPDATE meals SET isFav = :isFav WHERE idMeal = :id")
    void updateFavStatus(String id, boolean isFav);

    // Update isPlanned status for a meal by ID
    @Query("UPDATE meals SET isPlanned = :isPlanned WHERE idMeal = :id")
    void updatePlannedStatus(String id, boolean isPlanned);
}

