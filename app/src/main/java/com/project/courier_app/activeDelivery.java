package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activeDelivery extends AppCompatActivity {
    private RetrofitInterface rtfBase;
    Courier courier;
    String CourierUser, ID ,TOKEN, myDeliveryNuw, myDeliveryID;
    Button deliveredB,exceptionB,inTransitB;
    private TextView info;
    Delivery delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_delivery);
        rtfBase = RetrofitBase.getRetrofitInterface();
        deliveredB=(Button)findViewById(R.id.DELIVERED);
        exceptionB=(Button)findViewById(R.id.EXCEPTION);
        inTransitB=(Button)findViewById(R.id.IN_TRANSIT);

        info=(TextView)findViewById(R.id.info);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            ID = extras.getString("id");
            TOKEN=extras.getString("token");
        }


        inTransitB.setOnClickListener((v -> {
            HashMap<String, String> statusMap = new HashMap<String, String>();
            statusMap.put("status","IN_TRANSIT");
            Call<Void> call = rtfBase.registerDelivery("Bearer "+TOKEN,myDeliveryID,statusMap);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code()==200){
                        Toast.makeText(activeDelivery.this, "status updates",Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(activeDelivery.this, "status  not updates",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(activeDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }));


        exceptionB.setOnClickListener((v -> {
            HashMap<String, String> statusMap = new HashMap<String, String>();
            statusMap.put("status","EXCEPTION");
            Call<Void> call = rtfBase.registerDelivery("Bearer "+TOKEN,myDeliveryID,statusMap);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code()==200){
                        Toast.makeText(activeDelivery.this, "status updates",Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(activeDelivery.this, "status  not updates",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(activeDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }));


        deliveredB.setOnClickListener((v -> {
            HashMap<String, String> statusMap = new HashMap<String, String>();
            statusMap.put("status","DELIVERED");
            Call<Void> call = rtfBase.registerDelivery("Bearer "+TOKEN,myDeliveryID,statusMap);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200){
                    Toast.makeText(activeDelivery.this, "status updates",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(activeDelivery.this, "status  not updates",Toast.LENGTH_LONG).show();

                }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(activeDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }));


        Call<String> call= rtfBase.getUser(ID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200 ||response.code() == 204){

                    CourierUser=response.body();
                    courier=new Gson().fromJson(CourierUser, Courier.class) ;
                    if(courier.getCurrentDelivery()!=null) {
                        myDeliveryID = courier.getCurrentDelivery();


                        Call<String> call2= rtfBase.getDelivery(myDeliveryID);
                        call2.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.code() == 200 ||response.code() == 204) {

                                    myDeliveryNuw=response.body();
                                    delivery = new Gson().fromJson(myDeliveryNuw, Delivery.class);
                                    info.setText(delivery.toString());
                                   // Toast.makeText(activeDelivery.this, "status updates",Toast.LENGTH_LONG).show();

                                }
                                else{
                                //    Toast.makeText(activeDelivery.this, "status not change",Toast.LENGTH_LONG).show();

                                }
                                }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(activeDelivery.this, "something not good"+t.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });





                    }
                    else{
                        Log.i("bbbbbb",courier.getCurrentDelivery());
                    }
                 //   info.setText(delivery.toString());


                    Toast.makeText(activeDelivery.this, "good",Toast.LENGTH_LONG).show();


                }else{
                    Toast.makeText(activeDelivery.this, "something not good",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(activeDelivery.this, "something not good "+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });





    }

}