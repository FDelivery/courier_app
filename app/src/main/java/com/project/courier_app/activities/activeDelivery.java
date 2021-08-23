package com.project.courier_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.courier_app.R;
import com.project.courier_app.classes.Courier;
import com.project.courier_app.classes.Delivery;
import com.project.courier_app.classes.RetrofitBase;
import com.project.courier_app.classes.RetrofitInterface;

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
    HashMap<String, String> statusMap = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_delivery);
        rtfBase = RetrofitBase.getRetrofitInterface();
        deliveredB=findViewById(R.id.DELIVERED);
        exceptionB=findViewById(R.id.EXCEPTION);
        inTransitB=findViewById(R.id.IN_TRANSIT);

        info=(TextView)findViewById(R.id.info);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            ID = extras.getString("id");
            TOKEN=extras.getString("token");
        }

//we need to pull the user and the delivery from DB
        getUser();



//update that the delivery in transit
        inTransitB.setOnClickListener((v -> {
            updateStatus("IN_TRANSIT");
        }));

//update that we have a problem with this delivery
        exceptionB.setOnClickListener((v -> {
            updateStatus("EXCEPTION");

        }));

//update that the delivery is delivered successfully
        deliveredB.setOnClickListener((v -> {
            updateStatus("DELIVERED");
            updateMyCurrentDelivery();
        }));



    }

    //we need to pull the user from DB
    private void getUser(){
        Call<String> call= rtfBase.getUser(ID);
        call.enqueue(new Callback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200 ||response.code() == 204){

                    CourierUser=response.body();
                    courier=new Gson().fromJson(CourierUser, Courier.class) ;
                    if(!courier.getCurrentDelivery().equals("None")) {
                        myDeliveryID = courier.getCurrentDelivery();
                        getCurrentDelivery();
                    }

                }else Toast.makeText(activeDelivery.this, "user id not found",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(activeDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    //we need to pull the current delivery from DB
    private void getCurrentDelivery(){
        Call<String> call= rtfBase.getDelivery(myDeliveryID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200 ||response.code() == 204) {

                    myDeliveryNuw=response.body();
                    delivery = new Gson().fromJson(myDeliveryNuw, Delivery.class);
                    info.setText(delivery.toString());

                }
                else{
                        Toast.makeText(activeDelivery.this, " delivery id not found",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(activeDelivery.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }



    //after click on delivered we need to update that "no active delivery"
    private void updateMyCurrentDelivery(){

        HashMap<String, String> map=new HashMap<>();
        map.put("currentDelivery","None");
        Call<Void> call2= rtfBase.updateUser(ID,map);
        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.code()==200){
                    Toast.makeText(activeDelivery.this, "update",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(activeDelivery.this, "update fails",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(activeDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }


//update the delivery status
     private void updateStatus(String status){
         statusMap.put("status",status);
         Call<Void> call = rtfBase.registerDelivery("Bearer "+TOKEN,myDeliveryID,statusMap);
         call.enqueue(new Callback<Void>() {
             @Override
             public void onResponse(Call<Void> call, Response<Void> response) {
                 if(response.code()==200){
                     Toast.makeText(activeDelivery.this, "status updated",Toast.LENGTH_LONG).show();

                 }
                 else{
                     Toast.makeText(activeDelivery.this, "status not updates",Toast.LENGTH_LONG).show();

                 }
             }

             @Override
             public void onFailure(Call<Void> call, Throwable t) {
                 Toast.makeText(activeDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

             }
         });
    }



}