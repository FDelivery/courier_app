package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNewCourier extends AppCompatActivity {

    private EditText PasswordEt, Phone2, Phone1, EmailEt, Last, First,Vehicle;
    private Button Create,payPage;
   private RetrofitInterface rtfBase;
    private String ID,TOKEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_courier);
        First = findViewById(R.id.FirstName);
        PasswordEt = findViewById(R.id.passwordEt);
        Last = findViewById(R.id.LastName);
        Phone1 = findViewById(R.id.Phone1);
        Phone2 = findViewById(R.id.phone2);
        Create=findViewById(R.id.Create);
        payPage=findViewById(R.id.enter_bank);
        EmailEt = findViewById(R.id.EmailEt);
        Vehicle = findViewById(R.id.vehicle);
        rtfBase=RetrofitBase.getRetrofitInterface();


        payPage.setOnClickListener( (v) -> {
            Intent intent = new Intent(this, DetailsBank.class);
            startActivity(intent);
        });
        Create.setOnClickListener((v) -> {

            String email = EmailEt.getText().toString();
            String password = PasswordEt.getText().toString();
            String firstname = First.getText().toString();
            String phone1 = Phone1.getText().toString();
            String lastname = Last.getText().toString();
            String phone2 = Phone2.getText().toString();
            String vehicle = Vehicle.getText().toString();

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
                First.setError("This field is necessary");
                return;
            }
            if(phone1.isEmpty()) {
                Phone1.setError("This field is necessary");
                return;
            }
            if(lastname.isEmpty()) {
                Last.setError("This field is necessary");
                return;
            }
            if(vehicle.isEmpty()) {
                Vehicle.setError("choose one from: Bicycle , car ,  motorcycle");
                return;
            }
            else if(vehicle.equals("car") || vehicle.equals("Car") || vehicle.equals("CAR")){
                vehicle="CAR";
            }else if(vehicle.equals("Bicycle") || vehicle.equals("BICYCLE") || vehicle.equals("bicycle") || vehicle.equals("bicycl") || vehicle.equals("bcycle")){
                vehicle="BICYCLE";
            }else if(vehicle.equals("MOTORcycle") || vehicle.equals("MOTORCYCLE") || vehicle.equals("motorcycle") || vehicle.equals("Motorcycle") || vehicle.equals("motocycle")){
            vehicle="MOTORCYCLE";
        }else{
                Vehicle.setError("choose one from: Bicycle , car ,  motorcycle");
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

            Courier courier = phone2.isEmpty() ? new Courier(email,phone1,password,firstname,lastname,vehicle)
                    : new Courier(email, phone1,phone2,password,firstname,lastname, vehicle);

            handleRegister(courier);

        });
    }
//send courier and register
   private void handleRegister(Courier courier) {


       Call<String> call = rtfBase.register(courier); //we get id
       call.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
               if(response.code() == 200)
               {
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

   //connect the app auto after register
    private void connectToApp(Courier courier){

        HashMap<String, String> connect = new HashMap<>();
        connect.put("email",courier.getEmail()) ;
        connect.put("password",courier.getPassword());
        Call<String[]> call = rtfBase.connect(connect); //we get token and id
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if(response.code() == 200)
                {
                    assert response.body() != null;

                    ID=response.body()[1];
                    TOKEN=response.body()[0];

                    Toast.makeText(RegisterNewCourier.this, "registered successfully",Toast.LENGTH_LONG).show();
                    GetUser();
                }
                if(response.code() == 400)
                {
                    Toast.makeText(RegisterNewCourier.this, "fail to connect",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 401)
                {
                    Toast.makeText(RegisterNewCourier.this, "Email or password invalid",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(RegisterNewCourier.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    // get- in id, return user
    public void GetUser()
    {

        Intent intent = new Intent(this, CourierMain.class);
        Call<String> call = rtfBase.getUser(ID);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {

                    //success
                    intent.putExtra("CourierUserInGson",response.body());
                    intent.putExtra("id",ID);
                    intent.putExtra("token",TOKEN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }


                if(response.code() == 400 || response.code()==500)
                {
                    //failure
                    Toast.makeText(RegisterNewCourier.this, "this ID do not exist", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterNewCourier.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}