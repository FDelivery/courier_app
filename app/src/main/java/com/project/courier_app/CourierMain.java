package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
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
    private TextView welcome;
    private RetrofitInterface rtfBase;
    Courier courier;
    String FromIntent,ID,TOKEN;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_main);
        welcome=(TextView)findViewById(R.id.textViewWelcom);
        deliveryHistory=(ImageButton)findViewById(R.id.deliveryHistory);
        chooseDelivery=(ImageButton)findViewById(R.id.chooseDelivery);
        deliveryList=(ImageButton)findViewById(R.id.deliveryList);
        myprofile=(ImageButton)findViewById(R.id.myprofile);
        rtfBase = RetrofitBase.getRetrofitInterface();


        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            FromIntent = extras.getString("CourierUserInGson");
            courier=new Gson().fromJson(FromIntent, Courier.class);
            ID =extras.getString("id");
            TOKEN=extras.getString(("token"));
            welcome.setText("welcome "+courier.getFirstName()+" "+courier.getLastName());
            Log.i("11111",TOKEN);

        }


        deliveryList.setOnClickListener((v) -> {
Intent intent= new Intent(this, DeliveryTable.class);
            intent.putExtra("token",TOKEN);
Log.i("222222",TOKEN);
            startActivity(intent);
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

            Intent intent =new Intent(this, ChooseDelivery.class);
            Log.i("11111",TOKEN);
            intent.putExtra("token",TOKEN);
Log.i("abcd",TOKEN);
            intent.putExtra("CourierUserInGson",FromIntent);
            intent.putExtra("id",ID);
            startActivity(intent);
        });
    }





}