package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourierMain extends AppCompatActivity {
    private ImageButton deliveryHistory;
    private ImageButton chooseDelivery;
    private ImageButton deliveryList;
    private ImageButton myprofile;
    private RetrofitInterface rtfBase;

    String FromIntent,ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_main);

        deliveryHistory=(ImageButton)findViewById(R.id.deliveryHistory);
        chooseDelivery=(ImageButton)findViewById(R.id.chooseDelivery);
        deliveryList=(ImageButton)findViewById(R.id.deliveryList);
        myprofile=(ImageButton)findViewById(R.id.myprofile);
        rtfBase = RetrofitBase.getRetrofitInterface();


        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            FromIntent = extras.getString("CourierUserInGson");
            ID =extras.getString("id");

        }


        deliveryList.setOnClickListener((v) -> {
            startActivity(new Intent(this, DeliveryTable.class));
        });

        myprofile.setOnClickListener((v) -> {

            Intent intent =new Intent(this,CourierProfile.class);
            intent.putExtra("CourierUserInGson",FromIntent);
            intent.putExtra("id",ID);
            startActivity(intent);
        });

        deliveryHistory.setOnClickListener((v) -> {
            startActivity(new Intent(this, DeliveryHistory.class));
        });

        chooseDelivery.setOnClickListener((v) -> {
            startActivity(new Intent(this, ChooseDelivery.class));
        });
    }





}