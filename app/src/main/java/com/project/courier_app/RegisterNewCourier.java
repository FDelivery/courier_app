package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterNewCourier extends AppCompatActivity {

    private EditText FirstName, LastName, PasswordEt, Phone, EmailEt;
    private Button Create;
   private RetrofitInterface rtfBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_courier);
        FirstName = (EditText)findViewById(R.id.FirstName);
        PasswordEt = (EditText)findViewById(R.id.PasswordEt);
        LastName = (EditText)findViewById(R.id.LastName);
        Phone = (EditText)findViewById(R.id.Phone);
        Create=(Button)findViewById(R.id.Create);
        EmailEt = findViewById(R.id.EmailEt);


        Create.setOnClickListener((v) -> {

            String email = EmailEt.getText().toString();
            String password = PasswordEt.getText().toString();
            String firstname = FirstName.getText().toString();
            String phone = Phone.getText().toString();
            String lastname = LastName.getText().toString();

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
                FirstName.setError("This field is necessary");
                return;
            }
            if(phone.isEmpty()) {
                Phone.setError("This field is necessary");
                return;
            }
            if(lastname.isEmpty()) {
                LastName.setError("This field is necessary");
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


//            Business business = phone2.isEmpty() ? new Business(email, phone1, address, bName, password)
//                    : new Business(email, phone1, phone2, address, bName,password);
//
//            handleRegister(business);


        });
    }

    //still need to handle password...
//   private void handleRegister(Business business) {
//
//      HashMap<String, String> credentials = new HashMap<>();
//
//      credentials.put("name",business.getBusinessName());
//      credentials.put("email",business.getEmail());
//      credentials.put("password", business.getPassword());
//      credentials.put("address", business.getAddress());
//      credentials.put("phone1", business.getPhoneNumber1());
//      if(!business.getPhoneNumber2().isEmpty())
//          credentials.put("phone2", business.getPhoneNumber2());
//
//
//       Call<Void> call = rtfBase.register(credentials);
//        call.enqueue(new Callback<Void>() {
//           @Override
//           public void onResponse(Call<Void> call, Response<Void> response) {
//              if(response.code() == 200)
//                   Toast.makeText(getApplicationContext(), "registered successfully",Toast.LENGTH_LONG).show();
//                }
//                if(response.code() == 400)
//                {
//                    Toast.makeText(getApplicationContext(), "you already registered",Toast.LENGTH_LONG).show();
//                }
//           }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//              Toast.makeText(RegisterNewBusiness.this, t.getMessage(),Toast.LENGTH_LONG).show();
//          }
//       });
//   }

}