package com.project.courier_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.courier_app.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    private EditText NewPassword;
    private EditText ConformNewPassword;
    private Button ConformPasswordChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        NewPassword=(EditText)findViewById(R.id.NewPassword);
        ConformNewPassword=(EditText)findViewById(R.id.ConformNewPassword);
        ConformPasswordChange=(Button)findViewById(R.id.ConformPasswordChange);



        ConformPasswordChange.setOnClickListener((v) -> {


        });
    }


}