package com.project.courier_app;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText EmailEt;
    private EditText PasswordEt;
    private Button Connect;
    private TextView ForgotPassword;
    private TextView newCourier;
    private RetrofitInterface rtfBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rtfBase = RetrofitBase.getRetrofitInterface();
        EmailEt = (EditText)findViewById(R.id.Email);
        PasswordEt = (EditText)findViewById(R.id.Password);
        Connect=(Button)findViewById(R.id.Connect);
        ForgotPassword = findViewById(R.id.forgotPass);
        newCourier = findViewById(R.id.newCourier);

        Connect.setOnClickListener((v) -> {
            if(EmailEt.getText().toString().isEmpty())
            {
                EmailEt.setError("This field is necessary");
                return;
            }
            if(PasswordEt.getText().toString().isEmpty())
            {
                PasswordEt.setError("This field is necessary");
                return;
            }
            handleConnect();
        });
        ForgotPassword.setOnClickListener((v)->{

        });
        newCourier.setOnClickListener((v) -> {
            startActivity(new Intent(this, RegisterNewCourier.class));
        });




    }

    private void handleConnect() { //צריך שהשרת יחזיר לי אוביקט יוזר ואז אוכל להוסיף מה שצריך לפונקציה
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email",EmailEt.getText().toString());
        credentials.put("password",PasswordEt.getText().toString());
        Call<String> call = rtfBase.connect(credentials);
        Intent intent = new Intent(this, CourierProfile.class);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200)
                {
                    //success
                    Toast.makeText(MainActivity.this, "You have logged in successfully", Toast.LENGTH_LONG).show();
                    // business.setToken(response.body());
                    // intent.putExtra("token",response.body().toString()); //נשמור את הטוקן לאקטיביטי הבא
                    startActivity(intent);
                }
                if(response.code() == 400 || response.code() == 401)
                {
                    //failure
                    Toast.makeText(MainActivity.this, "log in failed-try again", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong" +t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
