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
        EmailEt = (EditText)findViewById(R.id.EmailEt);
        vehicle = (EditText)findViewById(R.id.vehicle);
        rtfBase=RetrofitBase.getRetrofitInterface();

        Create.setOnClickListener((v) -> {

            String email = EmailEt.getText().toString();
            String password = PasswordEt.getText().toString();
            String firstname = first.getText().toString();
            String phone1 = Phone1.getText().toString();
            String lastname = last.getText().toString();
            String phone2 = Phone2.getText().toString();
            String Vehicle = vehicle.getText().toString();

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
                vehicle.setError("choose one from: BICYCLE , CAR ,  MOTORCYCLE");
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

            Courier courier = phone2.isEmpty() ? new Courier(email,phone1,password,firstname,lastname,Vehicle)
                    : new Courier(email, phone1,phone2,password,firstname,lastname, Vehicle);
           // Log.i("asd",courier.getEmail()+" "+courier.getPrimaryPhone()+" "+courier.getPassword()+" "+courier.getFirstName()+" "+courier.getLastName()+" "+courier.getVehicle());

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

        HashMap<String, String> help = new HashMap<>();
        help.put("email",courier.getEmail()) ;
        help.put("password",courier.getPassword());
        Call<String[]> call = rtfBase.connect(help); //we get token and id
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if(response.code() == 200)
                {
                    assert response.body() != null;
                    courier.setToken(response.body()[0]);
                    courier.setId(response.body()[1]);

                    Toast.makeText(RegisterNewCourier.this, "successfully",Toast.LENGTH_LONG).show();
                    GetUser(courier.getId());
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
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(RegisterNewCourier.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    // get- in id, return user
    public void GetUser(String id) // need to know how to use in accepted user
    {

        Intent intent = new Intent(this, CourierMain.class);
        Log.i(("vvv"),id);
        Call<String> call = rtfBase.getUser(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {

                    //success
                   // Log.i("TEST123",response.body());
                    intent.putExtra("CourierUserInGson",response.body());
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
                Toast.makeText(RegisterNewCourier.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}