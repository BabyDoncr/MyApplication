package com.program.cherishtime.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkWrapper {
    private static NetworkWrapper wrapper = null;

    private NetworkWrapper() {

    }

    public static NetworkWrapper getInstance() {
        if (wrapper == null)
            wrapper = new NetworkWrapper();
        return wrapper;
    }

    public DesertApiClient getDesertApiClient() {
        return new Retrofit.Builder()
                .baseUrl(DesertApiClient.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DesertApiClient.class);
    }

    public DesertApiClient getDesertApiClientWithoutConverter() {
        return new Retrofit.Builder()
                .baseUrl(DesertApiClient.URL)
                .build()
                .create(DesertApiClient.class);
    }

    public DesertApiClient getDesertApiClientWithRxJava2CallAdapter() {
        return new Retrofit.Builder()
                .baseUrl(DesertApiClient.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DesertApiClient.class);
    }

    public DesertApiClient getDesertApiClientWithAppKey(String appKey) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor(appKey))
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
        return new Retrofit.Builder()
                .baseUrl(DesertApiClient.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(DesertApiClient.class);
    }

    public OpenWeatherApiClient getOpenWeatherApiClient() {
        return new Retrofit.Builder()
                .baseUrl(OpenWeatherApiClient.END_POINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApiClient.class);
    }
}
