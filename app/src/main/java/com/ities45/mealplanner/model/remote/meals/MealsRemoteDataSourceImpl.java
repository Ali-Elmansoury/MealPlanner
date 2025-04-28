package com.ities45.mealplanner.model.remote.meals;

import android.content.Context;

import com.ities45.mealplanner.model.pojo.MealResponse;
import com.ities45.mealplanner.model.remote.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDataSourceImpl implements IMealsRemoteDataSource{
    private static final String TAG = "MealsRemoteDataSourceImpl";
    private static MealsRemoteDataSourceImpl mealsRemoteDataSource = null;
    private Context context;
    private IMealService mealService;
    private RetrofitClient retrofitClient;

    private MealsRemoteDataSourceImpl(Context context){
        this.context = context;
        retrofitClient = RetrofitClient.getInstance(context);
        mealService = retrofitClient.createService(IMealService.class);
    }

    public static synchronized MealsRemoteDataSourceImpl getInstance(Context context){
        if (mealsRemoteDataSource == null){
            mealsRemoteDataSource = new MealsRemoteDataSourceImpl(context);
        }
        return  mealsRemoteDataSource;
    }

    @Override
    public void getMealsByCategoryNetworkCall(String category, IMealsNetworkCallback networkCallback) {
        Call<MealResponse> mealResponseCall = mealService.getMealsByCategory(category);
        mealResponseCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onGetMealsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onGetMealsFailure(throwable.getMessage());
            }
        });
    }

    @Override
    public void getMealsByAreaNetworkCall(String area, IMealsNetworkCallback networkCallback) {
        Call<MealResponse> mealResponseCall = mealService.getMealsByArea(area);
        mealResponseCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onGetMealsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onGetMealsFailure(throwable.getMessage());
            }
        });
    }

    @Override
    public void getMealsByIngredientNetworkCall(String ingredient, IMealsNetworkCallback networkCallback) {
        Call<MealResponse> mealResponseCall = mealService.getMealsByIngredient(ingredient);
        mealResponseCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onGetMealsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onGetMealsFailure(throwable.getMessage());
            }
        });
    }

    @Override
    public void searchMealsByNameNetworkCall(String mealName, IMealsNetworkCallback networkCallback) {
        Call<MealResponse> mealResponseCall = mealService.searchMealsByName(mealName);
        mealResponseCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onGetMealsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onGetMealsFailure(throwable.getMessage());
            }
        });
    }

    @Override
    public void searchMealsByFirstLetterNetworkCall(String firstLetter, IMealsNetworkCallback networkCallback) {
        Call<MealResponse> mealResponseCall = mealService.searchMealsByFirstLetter(firstLetter);
        mealResponseCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onGetMealsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onGetMealsFailure(throwable.getMessage());
            }
        });
    }

    @Override
    public void getMealDetailsByIdNetworkCall(String mealId, IMealsNetworkCallback networkCallback) {
        Call<MealResponse> mealResponseCall = mealService.getMealDetailsById(mealId);
        mealResponseCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onGetMealsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onGetMealsFailure(throwable.getMessage());
            }
        });
    }

    @Override
    public void getRandomMealNetworkCall(IMealsNetworkCallback networkCallback) {
        Call<MealResponse> mealResponseCall = mealService.getRandomMeal();
        mealResponseCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onGetMealsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onGetMealsFailure(throwable.getMessage());
            }
        });
    }
}
