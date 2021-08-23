package com.project.courier_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.courier_app.R;
import com.project.courier_app.classes.Courier;


public class CourierProfile extends AppCompatActivity {
    private TextView PhoneMP,Phone2MP;
    private TextView EmailMP,vehicleMP;
    private TextView TextMP;
    private Button EditMPage;
    private Button PassChangeMP;
    Courier courier;
    String CourierUser,ID,TOKEN;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_profile);

        EditMPage=findViewById(R.id.ChangeMP);
        EmailMP= findViewById(R.id.EmailMP);
        PassChangeMP=(Button)findViewById(R.id.PassChangeMP);
        TextMP=findViewById(R.id.TextMP);
        PhoneMP=findViewById(R.id.PhoneMP);
        Phone2MP=findViewById(R.id.Phone2MP);
        vehicleMP=findViewById(R.id.vehicleMP);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            CourierUser = extras.getString("CourierUserInGson");
            ID =extras.getString("id");
            TOKEN=extras.getString(("token"));
            courier = new Gson().fromJson(CourierUser, Courier.class);

        }
        TextMP.setText(courier.getFirstName()+ " "+courier.getLastName());
        EmailMP.setText("My email is: "+courier.getEmail());
        PhoneMP.setText("My phone is: "+courier.getPrimaryPhone());

        if((courier.getSecondaryPhone()!=null)&&(!courier.getSecondaryPhone().isEmpty()))
        {
            Phone2MP.setText("My secondary phone is: "+courier.getSecondaryPhone());
        }
        else{
            Phone2MP.setText("My secondary phone is: No number entered");

        }
        vehicleMP.setText("My vehicle is: "+courier.getVehicle());



        //change profile info
        EditMPage.setOnClickListener((v) -> {
            Intent intent =new Intent(this, EditMyProfile.class);
            intent.putExtra("id",ID);
            intent.putExtra("token",TOKEN);
            Toast.makeText(CourierProfile.this, "fill JUST what you need", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });


//change password
        PassChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, ChangePassword.class));
        });
    }
}