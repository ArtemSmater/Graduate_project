package com.example.dishescollection.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiStringFactory {

    private static Retrofit retrofit;
    private static ApiStringFactory apiStringFactory;
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private ApiStringFactory() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static ApiStringFactory getInstance() {
        if (apiStringFactory == null) {
            apiStringFactory = new ApiStringFactory();
        }
        return apiStringFactory;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
