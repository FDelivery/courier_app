package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CourierProfile extends AppCompatActivity {
    private EditText PhoneMP;
    private EditText EmailMP;
    private TextView TextMP;
    private Button ChangeMP;
    private Button PassChangeMP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_profile);

        ChangeMP=(Button)findViewById(R.id.ChangeMP);
        EmailMP=(EditText) findViewById(R.id.EmailMP);
        PassChangeMP=(Button)findViewById(R.id.PassChangeMP);
        TextMP=(TextView)findViewById(R.id.TextMP);
        PhoneMP=(EditText)findViewById(R.id.PhoneMP);

        ChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, EditMyProfile.class));
        });

        PassChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, ChangePassword.class));
        });
    }
}