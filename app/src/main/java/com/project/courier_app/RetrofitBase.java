package com.project.courier_app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.1.39:5000";

    private RetrofitBase() { }
    public static RetrofitInterface getRetrofitInterface()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitInterface.class);
    }
}
