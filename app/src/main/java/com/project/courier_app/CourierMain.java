package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class CourierMain extends AppCompatActivity {
    private ImageButton deliveryHistory;
    private ImageButton chooseDelivery;
    private ImageButton deliveryList;
    private ImageButton myprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_main);

        deliveryHistory=(ImageButton)findViewById(R.id.deliveryHistory);
        chooseDelivery=(ImageButton)findViewById(R.id.chooseDelivery);
        deliveryList=(ImageButton)findViewById(R.id.deliveryList);
        myprofile=(ImageButton)findViewById(R.id.myprofile);

        deliveryList.setOnClickListener((v) -> {
            startActivity(new Intent(this, DeliveryTable.class));
        });

        myprofile.setOnClickListener((v) -> {
            startActivity(new Intent(this, CourierProfile.class));
        });

        deliveryHistory.setOnClickListener((v) -> {
            startActivity(new Intent(this, DeliveryHistory.class));
        });

        chooseDelivery.setOnClickListener((v) -> {
            startActivity(new Intent(this, ChooseDelivery.class));
        });
    }
}