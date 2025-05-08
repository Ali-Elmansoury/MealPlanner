package com.ities45.mealplanner.model.local.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public class MealsLocalDataSourceImpl implements IMealsLocalDataSource{
    private Context context;
    private IMealsDao mealsDao;

    private static MealsLocalDataSourceImpl localDataSource = null;

    private MealsLocalDataSourceImpl(Context context){
        this.context = context;
        MealsDatabase db = MealsDatabase.getInstance(context.getApplicationContext());
        mealsDao = db.getMealsDao();
    }

    public static MealsLocalDataSourceImpl getInstance(Context context){
        if (localDataSource == null){
            localDataSource = new MealsLocalDataSourceImpl(context);
        }
        return localDataSource;
    }

    @Override
    public void insertLocalMeal(Meal meal) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mealsDao.insertMeal(meal);
            }
        }.start();
    }

    @Override
    public void insertLocalAllMeals(List<Meal> meals) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mealsDao.insertAll(meals);
            }
        }.start();
    }

    @Override
    public LiveData<List<Meal>> getAllLocalStoredMeals() {
        return mealsDao.getAllMeals();
    }

    @Override
    public LiveData<Meal> getLocalMealById(String id) {
        return mealsDao.getMealById(id);
    }

    @Override
    public void deleteLocalMeal(Meal meal) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mealsDao.deleteMeal(meal);
            }
        }.start();
    }

    @Override
    public void deleteAllLocalMeals() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mealsDao.deleteAllMeals();
            }
        }.start();
    }

    @Override
    public void updateLocalMeal(Meal meal) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mealsDao.updateMeal(meal);
            }
        }.start();
    }

    @Override
    public LiveData<List<Meal>> getLocalFavouriteMeals() {
        return mealsDao.getFavouriteMeals();
    }

    @Override
    public LiveData<List<Meal>> getLocalPlannedMeals() {
        return mealsDao.getPlannedMeals();
    }

    @Override
    public LiveData<List<Meal>> getLocalFavOrPlannedMeals() {
        return mealsDao.getFavOrPlannedMeals();
    }

    @Override
    public void updateFavStatus(String id, boolean isFav) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mealsDao.updateFavStatus(id, isFav);
            }
        }.start();
    }

    @Override
    public void updatePlannedStatus(String id, boolean isPlanned) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mealsDao.updatePlannedStatus(id, isPlanned);
            }
        }.start();
    }

    @Override
    public LiveData<List<Meal>> getPlannedMealsByDate(String date) {
        return mealsDao.getPlannedMealsByDate(date);
    }

    @Override
    public void syncFavorites(List<Meal> meals) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mealsDao.syncFavorites(meals);
            }
        };
    }

    @Override
    public void syncPlanned(List<Meal> meals) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mealsDao.syncPlanned(meals);
            }
        };
    }
}
