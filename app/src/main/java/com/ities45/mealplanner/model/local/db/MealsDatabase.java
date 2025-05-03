package com.ities45.mealplanner.model.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ities45.mealplanner.model.pojo.Meal;

@Database(entities = {Meal.class}, version = 1)
public abstract class MealsDatabase extends RoomDatabase {
    private static MealsDatabase instance = null;
    public abstract IMealsDao getMealsDao();
    public static synchronized MealsDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MealsDatabase.class, "meals_db").build();
        }
        return instance;
    }
}
