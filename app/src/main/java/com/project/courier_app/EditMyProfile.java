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

    private EditText EmailEP;
    private Button save;
    private TextView TextEP;
    private EditText PhoneEP;
    private EditText PhoneEP2;
    private EditText FirstNameEP;
    String FromIntent;
    private RetrofitInterface rtfBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);

        rtfBase = RetrofitBase.getRetrofitInterface();
        save=(Button)findViewById(R.id.save);
        EmailEP=(EditText) findViewById(R.id.EmailEP); //
        FirstNameEP=(EditText)findViewById(R.id.FirstNameEP); //
        TextEP=(TextView)findViewById(R.id.TextEP);
        PhoneEP=(EditText)findViewById(R.id.PhoneEP);//
        PhoneEP2=(EditText)findViewById(R.id.Phone2EP);//

        Bundle extras = getIntent().getExtras();



        if(extras!=null)
            FromIntent = extras.getString("id");





        save.setOnClickListener((v) -> {
            updateUser(FromIntent);

        });
    }



    private void updateUser(String id)
    {
        HashMap<String, String> map=new HashMap<>();
        String emailText= EmailEP.getText().toString();
        String NameText= FirstNameEP.getText().toString();
        String Phone1Text= PhoneEP.getText().toString();
        String Phone2Text= PhoneEP2.getText().toString();



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