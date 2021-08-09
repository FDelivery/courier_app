package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNewCourier extends AppCompatActivity {

    private EditText PasswordEt, Phone2, Phone1, EmailEt, last, first,vehicle;
    private Button Create;
   private RetrofitInterface rtfBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_courier);
        first = (EditText)findViewById(R.id.FirstName);
        PasswordEt = (EditText)findViewById(R.id.passwordEt);
        last = (EditText)findViewById(R.id.LastName);
        Phone1 = (EditText)findViewById(R.id.Phone1);
        Phone2 = (EditText)findViewById(R.id.phone2);
        Create=(Button)findViewById(R.id.Create);
        EmailEt = findViewById(R.id.EmailEt);
        vehicle = findViewById(R.id.vehicle);


        Create.setOnClickListener((v) -> {

            String email = EmailEt.getText().toString();
            String password = PasswordEt.getText().toString();
            String firstname = first.getText().toString();
            String phone1 = Phone1.getText().toString();
            String lastname = last.getText().toString();
            String phone2 = Phone2.getText().toString();
            String Vehicle = vehicle.getText().toString();
            rtfBase = RetrofitBase.getRetrofitInterface();

            //we need to check that the required fields are not empty
            if(email.isEmpty()) {
                EmailEt.setError("This field is necessary");
                return;
            }
            if(password.isEmpty()) {
                PasswordEt.setError("This field is necessary");
                return;
            }
            if(firstname.isEmpty()) {
                first.setError("This field is necessary");
                return;
            }
            if(phone1.isEmpty()) {
                Phone1.setError("This field is necessary");
                return;
            }
            if(lastname.isEmpty()) {
                last.setError("This field is necessary");
                return;
            }
            if(Vehicle.isEmpty()) {
                vehicle.setError("This field is necessary");
                return;
            }
            //check whether the given email address is valid
            if(!validations.isValidEmail(email))
            {
                EmailEt.setError("This email address is not valid");
                return;
            }
            //check whether the given password is valid-Minimum eight characters, at least one letter and one number
            if(!validations.isValidPassword(password))
            {
                PasswordEt.setError("This password is not valid, password needs minimum eight characters, at least one letter and one number");
                return;
            }

            Courier courier = phone2.isEmpty() ? new Courier(email, phone1,password,firstname,lastname, Vehicle)
                    : new Courier(email, phone1,  phone2,password,firstname,lastname, Vehicle);

            handleRegister(courier);

        });
    }

   private void handleRegister(Courier courier) {


       Call<String> call = rtfBase.register(courier); //we get id
       call.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
               if(response.code() == 200)
               {
                   courier.setId(response.body());
                   Toast.makeText(RegisterNewCourier.this, "registered successfully",Toast.LENGTH_LONG).show();
                   connectToApp(courier);


               }
               if(response.code() == 400)
               {
                   Toast.makeText(RegisterNewCourier.this, "you already registered",Toast.LENGTH_LONG).show();

               }
               if(response.code() == 404)
               {
                   Toast.makeText(RegisterNewCourier.this, "something wrong",Toast.LENGTH_LONG).show();

               }
           }


           @Override
           public void onFailure(Call<String> call, Throwable t) {
               Toast.makeText(RegisterNewCourier.this, t.getMessage(),Toast.LENGTH_LONG).show();
           }
       });
   }
    private void connectToApp(Courier courier){
        Intent intent = new Intent(this, CourierProfile.class);

        HashMap<String, String> help = new HashMap<>();
        help.put("email",courier.getEmail()) ;
        help.put("password",courier.getPassword());
        Call<String> call = rtfBase.connect(help); //we get token
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200)
                {
                    courier.setToken(response.body());
                    Toast.makeText(RegisterNewCourier.this, "successfully",Toast.LENGTH_LONG).show();
                    // intent.putExtra("user",business);
                    // System.out.println("--------------------"+business.getToken());
                    startActivity(intent);

                }
                if(response.code() == 400)
                {
                    Toast.makeText(RegisterNewCourier.this, "we have a problem",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 401)
                {
                    Toast.makeText(RegisterNewCourier.this, "Email or password invalid",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterNewCourier.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}