package com.ities45.mealplanner.model.local.networklistener;

public interface INetworkStatusListener {
    void onNetworkAvailable();
    void onNetworkLost();
}
