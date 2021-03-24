package com.project.courier_app;
// noting changed, completely copied from Business app
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface
{
    //  @POST("/connect")
    //  Call<Business> connect(@Body HashMap<String, String> map);

    @POST("/register")
    Call<Void> register(@Body HashMap<String, String> map);

    @GET("/user")
    Call<Void> getUser(@Query("id") String id );

    @POST("/deliveries")
    Call<Void> insertNewDelivery(@Body Delivery d);

}
