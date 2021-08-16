package com.project.courier_app;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

 import android.util.Log;
 import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText EmailEt;
    private EditText PasswordEt;
    private Button logIn;
    private TextView ForgotPassword;
    private TextView createNewCourier;
    private RetrofitInterface rtfBase;
    private String TOKEN,ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rtfBase = RetrofitBase.getRetrofitInterface();
        EmailEt = (EditText)findViewById(R.id.Email);
        PasswordEt = (EditText)findViewById(R.id.Password);
        logIn=(Button)findViewById(R.id.Connect);
        ForgotPassword = findViewById(R.id.forgotPass);
        createNewCourier = findViewById(R.id.newCourier);

        logIn.setOnClickListener((v) -> {
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
           // Intent intent= new Intent(this, ChangePassword.class); ///לעבור לאקטיביטי של שכחתי סיסמה

        });
        createNewCourier.setOnClickListener((v) -> {
           Intent intent= new Intent(this, RegisterNewCourier.class);
            startActivity(intent);
        });




    }

    private void handleConnect() {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email",EmailEt.getText().toString());
        credentials.put("password",PasswordEt.getText().toString());
        Call<String[]> call = rtfBase.connect(credentials);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if(response.code() == 200)
                {
                    //success
                    Toast.makeText(MainActivity.this, "You have logged in successfully", Toast.LENGTH_LONG).show();
                    String[] tokenAndID = new String[2];
                    tokenAndID=response.body();
                    TOKEN=tokenAndID[0];
                    ID=tokenAndID[1];
                    GetUser(ID);

                }
                if(response.code() == 400 || response.code() == 401)
                {
                    //failure
                    Toast.makeText(MainActivity.this, "log in failed-try again", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong" +t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // get- in id, return user
    public void GetUser(String id)
    {

        Intent intent = new Intent(this, CourierMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Call<String> call = rtfBase.getUser(id);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {
                    //success
                    intent.putExtra("CourierUserInGson",response.body());
                    intent.putExtra("id",id);
                    intent.putExtra("token",TOKEN);

                    startActivity(intent);
                }


                if(response.code() == 400 || response.code()==500)
                {
                    //failure
                    Toast.makeText(MainActivity.this, "this ID do not exist", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }


}
