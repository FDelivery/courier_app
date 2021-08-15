package com.project.courier_app;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface
{

    @POST("/api/v1/auth/login/") //return array [token,id]->user
    Call<String[]> connect(@Body HashMap<String, String> map);

    @POST("/api/v1/auth/register/") //return userid
    Call<String> register(@Body Courier c);

    @GET("/api/v1/users/{user_id}") //return gson string (user)
    Call<String> getUser(@Path("user_id") String id);

    @PUT("/api/v1/users/{user_id}") //return void
    Call<Void> updateUser(@Path("user_id") String id,@Body HashMap<String, String> map);


    /////////////////////////////////////////////////////////////////////////////////

    @PUT("/api/v1/deliveriesRef/{delivery_id}/") //return void -- update status                       להוסיף בשרת
    Call<Void> registerDelivery(@Header("Authorization") String token,@Path("delivery_id") String id, @Query("status") String status);

    @GET("/api/v1/deliveriesRef/") ////return gson string (Delivery)
    Call<List<String>> getDeliveries(@Query("status") String status);

    @GET("/api/v1/deliveriesRef/{delivery_id}/") ////return gson string (Delivery)
    Call<String> getDelivery(@Path("delivery_id") String id);


}
