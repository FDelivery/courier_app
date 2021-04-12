package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditMyProfile extends AppCompatActivity {

    private EditText EmailEP;
    private Button ChangeEP;
    private TextView TextEP;
    private EditText PhoneEP;
    private EditText LastNameEP;
    private EditText FirstNameEP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);


        ChangeEP=(Button)findViewById(R.id.ChangeEP);
        EmailEP=(EditText) findViewById(R.id.EmailEP);
        FirstNameEP=(EditText)findViewById(R.id.FirstNameEP);
        TextEP=(TextView)findViewById(R.id.TextEP);
        PhoneEP=(EditText)findViewById(R.id.PhoneEP);
        LastNameEP=(EditText)findViewById(R.id.LastNameEP);

        ChangeEP.setOnClickListener((v) -> {
            startActivity(new Intent(this, CourierProfile.class));
        });
    }
}