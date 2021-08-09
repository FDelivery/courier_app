package com.project.courier_app;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface
{
    @POST("/api/v1/auth/login/") //return token
    Call<String> connect(@Body HashMap<String, String> map);

    @POST("/api/v1/auth/register/") //return userid
    Call<String> register(@Body Courier b);

    @GET("/user")
    Call<Void> getUser(@Query("id") String id );

    @POST("/deliveries")
    Call<Void> insertNewDelivery(@Body Delivery d);

}
