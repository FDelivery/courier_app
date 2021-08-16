package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMyProfile extends AppCompatActivity {

    private EditText EmailEP,VehicleEP;
    private Button save;
    private TextView TextEP;
    private EditText PhoneEP;
    private EditText PhoneEP2;
    private EditText FirstNameEP,LastNameEP;
    private RetrofitInterface rtfBase;
    private String ID,TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);

        rtfBase = RetrofitBase.getRetrofitInterface();
        save=(Button)findViewById(R.id.save);
        EmailEP=(EditText) findViewById(R.id.EmailEP);
        FirstNameEP=(EditText)findViewById(R.id.FirstNameEP);
        LastNameEP=(EditText)findViewById(R.id.LastNameEP);
        TextEP=(TextView)findViewById(R.id.TextEP);
        PhoneEP=(EditText)findViewById(R.id.PhoneEP);
        PhoneEP2=(EditText)findViewById(R.id.Phone2EP);
        VehicleEP=(EditText)findViewById(R.id.vehicleEP);

        Bundle extras = getIntent().getExtras();



        if(extras!=null) {
            ID = extras.getString("id");
            TOKEN = extras.getString(("token"));
        }




        save.setOnClickListener((v) -> {
            updateUser(ID);

        });
    }



    private void updateUser(String id)
    {
        HashMap<String, String> map=new HashMap<>();
        String emailText= EmailEP.getText().toString();
        String NameText= FirstNameEP.getText().toString();
        String Phone1Text= PhoneEP.getText().toString();
        String Phone2Text= PhoneEP2.getText().toString();
        String LastNameText= LastNameEP.getText().toString();
        String VehicleText= VehicleEP.getText().toString();


        if(!emailText.isEmpty()) {
            map.put("email",emailText);
        }
        if(!NameText.isEmpty()) {
            map.put("firstName",NameText);
        }
        if(!Phone1Text.isEmpty())
        {
            map.put("primaryPhone",Phone1Text);
        }
        if(!Phone2Text.isEmpty()) {
            map.put("secondaryPhone",Phone2Text);
        }
        if(!LastNameText.isEmpty())
        {
            map.put("lastName",LastNameText);
        }

        if(!VehicleText.isEmpty()) {
            if((VehicleText.equals("car") || VehicleText.equals("Car") || VehicleText.equals("CAR"))) {
                map.put("vehicle", "CAR");
            }
            else if(VehicleText.equals("Bicycle") || VehicleText.equals("BICYCLE") || VehicleText.equals("bicycle") || VehicleText.equals("bicycl") || VehicleText.equals("bcycle")){
                map.put("vehicle","BICYCLE");
            }else if(VehicleText.equals("MOTORcycle") || VehicleText.equals("MOTORCYCLE") || VehicleText.equals("motorcycle") || VehicleText.equals("Motorcycle") || VehicleText.equals("motocycle")|| VehicleText.equals("motorbcycle")){
                map.put("vehicle","MOTORCYCLE");
            }else{
                VehicleEP.setError("choose one from: Bicycle , car ,  motorcycle");
                return;
            }
        }



        Call<Void> call = rtfBase.updateUser(id,map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200)
                {

                    Toast.makeText(EditMyProfile.this, "We update successfully", Toast.LENGTH_LONG).show();
                    GetUser(id);
                }

                if(response.code() == 400 || response.code()==500)
                {
                    //failure
                    Toast.makeText(EditMyProfile.this, "this ID do not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditMyProfile.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    // get- in id, return user
    public void GetUser(String id)
    {

        Intent intent = new Intent(this, CourierMain.class);

        Call<String> call = rtfBase.getUser(id);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {


                    intent.putExtra("CourierUserInGson",response.body());
                    intent.putExtra("id",id);
                    intent.putExtra("token",TOKEN);
                    startActivity(intent);

                }


                if(response.code() == 400 || response.code()==500)
                {
                    //failure
                    Toast.makeText(EditMyProfile.this, "this ID do not exist", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EditMyProfile.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }








}