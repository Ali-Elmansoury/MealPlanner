package com.ities45.mealplanner.model.remote.categories;

import android.content.Context;

import com.ities45.mealplanner.model.pojo.CategoryResponse;
import com.ities45.mealplanner.model.remote.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRemoteDataSourceImpl implements ICategoriesRemoteDataSource{
    private static final String TAG = "CategoriesRemoteDataSourceImpl";
    private static CategoriesRemoteDataSourceImpl categoriesRemoteDataSource = null;
    private Context context;
    private ICategoryService categoryService;
    private RetrofitClient retrofitClient;

    private CategoriesRemoteDataSourceImpl(Context context){
        this.context = context;
        retrofitClient = RetrofitClient.getInstance(context);
        categoryService = retrofitClient.createService(ICategoryService.class);
    }

    public static synchronized CategoriesRemoteDataSourceImpl getInstance(Context context){
        if (categoriesRemoteDataSource == null){
            categoriesRemoteDataSource = new CategoriesRemoteDataSourceImpl(context);
        }
        return categoriesRemoteDataSource;
    }


    @Override
    public void getAllMealCategoriesNetworkCallback(ICategoriesNetworkCallback networkCallback) {
        Call<CategoryResponse> categoryResponseCall = categoryService.getAllMealCategories();
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                networkCallback.onGetCategoriesSuccess(response.body().getCategories());
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                networkCallback.onGetCategoriesFailure(throwable.getMessage());
            }
        });
    }

    @Override
    public void listAllCategoriesNamesNetworkCallback(ICategoriesNetworkCallback networkCallback) {
        Call<CategoryResponse> categoryResponseCall = categoryService.listAllCategoriesNames();
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                networkCallback.onGetCategoriesSuccess(response.body().getCategories());
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                networkCallback.onGetCategoriesFailure(throwable.getMessage());
            }
        });
    }
}
