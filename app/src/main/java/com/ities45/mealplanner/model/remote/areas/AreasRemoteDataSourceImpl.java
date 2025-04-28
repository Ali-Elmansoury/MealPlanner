package com.ities45.mealplanner.model.remote.areas;

import android.content.Context;

import com.ities45.mealplanner.model.pojo.AreaResponse;
import com.ities45.mealplanner.model.remote.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreasRemoteDataSourceImpl implements IAreasRemoteDataSource{
    private static final String TAG = "AreasRemoteDataSourceImpl";
    private static AreasRemoteDataSourceImpl areasRemoteDataSource = null;
    private Context context;
    private IAreaService areaService;
    private RetrofitClient retrofitClient;

    private AreasRemoteDataSourceImpl(Context context){
        this.context = context;
        retrofitClient = RetrofitClient.getInstance(context);
        areaService = retrofitClient.createService(IAreaService.class);
    }

    public static synchronized AreasRemoteDataSourceImpl getInstance(Context context){
        if (areasRemoteDataSource == null){
            areasRemoteDataSource = new AreasRemoteDataSourceImpl(context);
        }
        return areasRemoteDataSource;
    }

    @Override
    public void listAllAreasNamesNetworkCallback(IAreasNetworkCallback networkCallback) {
        Call<AreaResponse> areaResponseCall = areaService.listAllAreasNames();
        areaResponseCall.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                networkCallback.onGetAreasSuccess(response.body().getAreas());
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable throwable) {
                networkCallback.onGetAreasFailure(throwable.getMessage());
            }
        });
    }
}
