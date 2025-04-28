package com.ities45.mealplanner.model.remote.areas;

import com.ities45.mealplanner.model.pojo.AreaResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IAreaService {
    // List all areas (names only)
    @GET("list.php?a=list")
    Call<AreaResponse> listAllAreasNames();
}
