package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

public class CourierMain extends AppCompatActivity {
    private ImageButton deliveryHistory;
    private ImageButton chooseDeliveryFromList;
    private ImageButton activeDelivery;
    private ImageButton myProfile;
    private TextView welcome;
    private RetrofitInterface rtfBase;
    Courier courier;
    String CourierUser,ID,TOKEN;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_main);
        welcome=(TextView)findViewById(R.id.textViewWelcom);
        deliveryHistory=(ImageButton)findViewById(R.id.deliveryHistory);
        chooseDeliveryFromList=(ImageButton)findViewById(R.id.chooseDelivery);
        activeDelivery=(ImageButton)findViewById(R.id.deliveryList);
        myProfile=(ImageButton)findViewById(R.id.myprofile);
        rtfBase = RetrofitBase.getRetrofitInterface();


        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            CourierUser = extras.getString("CourierUserInGson");
            courier=new Gson().fromJson(CourierUser, Courier.class);
            ID =extras.getString("id");
            TOKEN=extras.getString(("token"));
            welcome.setText("welcome "+courier.getFirstName());

        }


        activeDelivery.setOnClickListener((v) -> {
Intent intent= new Intent(this, activeDelivery.class); //לבדוק אפשרות שיפנה לאותו אקטיביטי של showChosen
            intent.putExtra("token",TOKEN);
            intent.putExtra("id",ID);

            startActivity(intent);
        });

        myProfile.setOnClickListener((v) -> {

            Intent intent =new Intent(this,CourierProfile.class);
            intent.putExtra("CourierUserInGson",CourierUser);
            intent.putExtra("token",TOKEN);
            intent.putExtra("id",ID);
            startActivity(intent);
        });

        deliveryHistory.setOnClickListener((v) -> {
           Intent intent= new Intent(this, DeliveryHistory.class);
            intent.putExtra("CourierUserInGson",CourierUser);
            intent.putExtra("token",TOKEN);
            intent.putExtra("id",ID);
            startActivity(intent);
        });

        chooseDeliveryFromList.setOnClickListener((v) -> {

            Intent intent =new Intent(this, ChooseDelivery.class);
            intent.putExtra("token",TOKEN);
            intent.putExtra("CourierUserInGson",CourierUser);
            intent.putExtra("id",ID);
            startActivity(intent);
        });
    }





}