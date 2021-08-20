package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class showChosenFromHistory extends AppCompatActivity {
    private TextView showInfo;
    String deliveryFromIntent,IDDELIVERY;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chosen_from_history);

        showInfo = (TextView)findViewById(R.id.showInfo);
        Delivery delivery;
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            IDDELIVERY=extras.getString("idDelivery");
            deliveryFromIntent=extras.getString("delivery");
            delivery=new Gson().fromJson(deliveryFromIntent, Delivery.class) ;
            showInfo.setText("ID: "+IDDELIVERY+"\n" +delivery.toString());


        }





    }
}