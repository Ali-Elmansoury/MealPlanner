package com.ities45.mealplanner.model.local.networklistener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class NetworkManager {
    private Context context;
    private ConnectivityManager.NetworkCallback networkCallback;
    private Handler mainHandler;
    private INetworkStatusListener listener;
    private static NetworkManager manager;

    private NetworkManager(Context context, INetworkStatusListener listener) {
        this.context = context;
        this.listener = listener;
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public static NetworkManager getInstance(Context context, INetworkStatusListener listener){
        if (manager == null){
            manager = new NetworkManager(context, listener);
        }
        return manager;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    public void registerNetworkCallback() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    Log.d("NetworkCallback", "Network is available");
                    mainHandler.post(() -> listener.onNetworkAvailable());
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    Log.d("NetworkCallback", "Network is lost");
                    mainHandler.post(() -> listener.onNetworkLost());
                }
            };
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        }
    }

    public void unregisterNetworkCallback() {
        if (networkCallback != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                cm.unregisterNetworkCallback(networkCallback);
            }
            networkCallback = null;
        }
    }
}
