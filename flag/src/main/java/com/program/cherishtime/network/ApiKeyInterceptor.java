package com.program.cherishtime.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    private static final String QUERY_APP_ID = "appKey";

    private final String mApiKey;

    public ApiKeyInterceptor(String apiKey) {
        mApiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter(QUERY_APP_ID, mApiKey).build();
        Request newRequest = chain.request().newBuilder().url(url).build();
        return chain.proceed(newRequest);
    }
}
