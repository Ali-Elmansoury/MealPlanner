package com.ities45.mealplanner.model.local.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public class MealsLocalDataSourceImpl implements IMealsLocalDataSource{
    private Context context;
    private IMealsDao mealsDao;
    private LiveData<List<Meal>> storedLocalMeals;

    private static MealsLocalDataSourceImpl localDataSource = null;

    private MealsLocalDataSourceImpl(Context context){
        this.context = context;
        MealsDatabase db = MealsDatabase.getInstance(context.getApplicationContext());
        mealsDao = db.getMealsDao();
        storedLocalMeals = mealsDao.getAllMeals();
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
        return storedLocalMeals;
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
}
