package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


public class CourierProfile extends AppCompatActivity {
    private TextView PhoneMP;
    private TextView EmailMP;
    private TextView TextMP;
    private Button ChangeMP;
    private Button PassChangeMP;
    Courier courier;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_profile);

        ChangeMP=(Button)findViewById(R.id.ChangeMP);
        EmailMP=(TextView) findViewById(R.id.EmailMP);
        PassChangeMP=(Button)findViewById(R.id.PassChangeMP);
        TextMP=(TextView)findViewById(R.id.TextMP);
        PhoneMP=(TextView)findViewById(R.id.PhoneMP);
        String FromIntent,ID;
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            FromIntent = extras.getString("CourierUserInGson");
            ID =extras.getString("id");
            courier = new Gson().fromJson(FromIntent, Courier.class);
            courier.setId(ID);

        }
        TextMP.setText("welcome "+courier.getFirstName()+ " "+courier.getLastName());
        EmailMP.setText("My email is: "+courier.getEmail());
        PhoneMP.setText("My phone is: "+courier.getPrimaryPhone());


        ChangeMP.setOnClickListener((v) -> {
            Intent intent2 =new Intent(this, EditMyProfile.class);
            intent2.putExtra("id",courier.getId());
            Toast.makeText(CourierProfile.this, "fill JUST what you need", Toast.LENGTH_LONG).show();
            startActivity(intent2);
        });

        PassChangeMP.setOnClickListener((v) -> {
            Intent intent =new Intent(this, ChangePassword.class);
            intent.putExtra("id",courier.getId());
            startActivity(intent);
        });
    }
}