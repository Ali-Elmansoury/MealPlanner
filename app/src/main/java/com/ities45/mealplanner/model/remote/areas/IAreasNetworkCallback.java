package com.ities45.mealplanner.model.remote.areas;

import com.ities45.mealplanner.model.pojo.Area;

import java.util.List;

public interface IAreasNetworkCallback {
    void onGetAreasSuccess(List<Area> areas);
    void onGetAreasFailure(String errMsg);
}
