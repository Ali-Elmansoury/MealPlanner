package com.ities45.mealplanner.model.remote.ingredients;

import android.content.Context;

import com.ities45.mealplanner.model.pojo.IngredientResponse;
import com.ities45.mealplanner.model.remote.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientsRemoteDataSourceImpl implements I_IngredientsRemoteDataSource{
    private static final String TAG = "IngredientsRemoteDataSourceImpl";
    private static IngredientsRemoteDataSourceImpl ingredientsRemoteDataSource = null;
    private Context context;
    private I_IngredientService ingredientService;
    private RetrofitClient retrofitClient;

    private IngredientsRemoteDataSourceImpl(Context context){
        this.context = context;
        retrofitClient = RetrofitClient.getInstance(context);
        ingredientService = retrofitClient.createService(I_IngredientService.class);
    }

    public static synchronized IngredientsRemoteDataSourceImpl getInstance(Context context){
        if (ingredientsRemoteDataSource == null){
            ingredientsRemoteDataSource = new IngredientsRemoteDataSourceImpl(context);
        }
        return ingredientsRemoteDataSource;
    }

    @Override
    public void listAllIngredientsNetworkCallback(I_IngredientsNetworkCallback networkCallback) {
        Call<IngredientResponse> ingredientResponseCall = ingredientService.listAllIngredients();
        ingredientResponseCall.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                networkCallback.onGetIngredientsSuccess(response.body().getIngredients());
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable throwable) {
                networkCallback.onGetIngredientsFailure(throwable.getMessage());
            }
        });
    }
}
