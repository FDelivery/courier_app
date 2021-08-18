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

        EditMPage=(Button)findViewById(R.id.ChangeMP);
        EmailMP=(TextView) findViewById(R.id.EmailMP);
        PassChangeMP=(Button)findViewById(R.id.PassChangeMP);
        TextMP=(TextView)findViewById(R.id.TextMP);
        PhoneMP=(TextView)findViewById(R.id.PhoneMP);
        Phone2MP=(TextView)findViewById(R.id.Phone2MP);
        vehicleMP=(TextView)findViewById(R.id.vehicleMP);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            CourierUser = extras.getString("CourierUserInGson");
            ID =extras.getString("id");
            TOKEN=extras.getString(("token"));
            courier = new Gson().fromJson(CourierUser, Courier.class);
            courier.setId(ID);

        }
        TextMP.setText(courier.getFirstName()+ " "+courier.getLastName());
        EmailMP.setText("My email is: "+courier.getEmail());
        Log.i("ppppp",courier.getEmail());
        PhoneMP.setText("My phone is: "+courier.getPrimaryPhone());
        PhoneMP.setText("My secondary phone is: "+courier.getSecondaryPhone());

        if((courier.getSecondaryPhone()!=null)&&(!courier.getSecondaryPhone().isEmpty()))
        {
            Phone2MP.setText("My second phone is: "+courier.getSecondaryPhone());
        }
        else{
            Phone2MP.setText("My second phone is: No number entered");

        }
        vehicleMP.setText("My vehicle is: "+courier.getVehicle());


        EditMPage.setOnClickListener((v) -> {
            Intent intent2 =new Intent(this, EditMyProfile.class);
            intent2.putExtra("id",ID);
            intent2.putExtra("token",TOKEN);
            Toast.makeText(CourierProfile.this, "fill JUST what you need", Toast.LENGTH_LONG).show();
            startActivity(intent2);
        });

        PassChangeMP.setOnClickListener((v) -> {
            Intent intent =new Intent(this, ChangePassword.class);
            startActivity(intent);
        });
    }
}