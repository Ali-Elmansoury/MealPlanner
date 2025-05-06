package com.ities45.mealplanner.model.remote.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String TAG = "RetrofitClient";
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static RetrofitClient retrofit = null;
    private Context context;
    private static Retrofit builder = null;

    private RetrofitClient(Context context) {
        this.context = context;

        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                // Interceptor to cache responses, skipping cache for random.php
                .addNetworkInterceptor(chain -> {
                    okhttp3.Request request = chain.request();
                    okhttp3.Response response = chain.proceed(request);

                    if (request.url().encodedPath().contains("random.php")) {
                        // Disable caching for random meal endpoint
                        return response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + 60 * 60 * 24)
                                .build();
                    }

                    // Default cache policy (1 day)
                    return response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + 60 * 60 * 24)
                            .build();
                })
                // Offline cache interceptor
                .addInterceptor(chain -> {
                    okhttp3.Request request = chain.request();
                    if (!isNetworkAvailable()) {
                        // Use cache for offline access up to 7 days
                        request = request.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                                .build();
                    }
                    return chain.proceed(request);
                })
                .build();


        builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static RetrofitClient getInstance(Context context) {
        if (retrofit == null) {
            synchronized (RetrofitClient.class) {
                if (retrofit == null) { // Double-check locking
                    retrofit = new RetrofitClient(context);
                }
            }
        }
        return retrofit;
    }

    public <S> S createService(Class<S> serviceClass) {
        S locBuilder = null;
        if (builder != null)
        {
            locBuilder = builder.create(serviceClass);
        }
        return locBuilder;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
