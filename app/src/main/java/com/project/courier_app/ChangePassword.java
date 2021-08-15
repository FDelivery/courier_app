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

public class ChangePassword extends AppCompatActivity {
    private EditText NewPassword;
    private EditText ConformNewPassword;
    private Button ConformPasswordChange;
    String FromIntent;
    private RetrofitInterface rtfBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        NewPassword=(EditText)findViewById(R.id.NewPassword);
        ConformNewPassword=(EditText)findViewById(R.id.ConformNewPassword);
        ConformPasswordChange=(Button)findViewById(R.id.ConformPasswordChange);
        rtfBase = RetrofitBase.getRetrofitInterface();

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
            FromIntent = extras.getString("id");


        ConformPasswordChange.setOnClickListener((v) -> {
            ///updateUser(FromIntent);

        });
    }

/*

    private void updateUser(String id)
    {

        String pass= NewPassword.getText().toString();
        String pass2= ConformNewPassword.getText().toString();


        if(!pass.equals(pass2)) {
            Toast.makeText(ChangePassword.this, "the passwords not same", Toast.LENGTH_LONG).show();
            return;


        }
        else if(pass.isEmpty() || pass2.isEmpty())
        {
            Toast.makeText(ChangePassword.this, "fill the password twice", Toast.LENGTH_LONG).show();
            return;
        }else{
            Call<Void> call = rtfBase.updatePass(id,pass);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 200)
                    {

                        Toast.makeText(ChangePassword.this, "We update successfully", Toast.LENGTH_LONG).show();
                        GetUser(id);
                    }

                    if(response.code() == 400 || response.code()==500)
                    {
                        //failure
                        Toast.makeText(ChangePassword.this, "this ID do not exist", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ChangePassword.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }





    }
*/


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
                    Toast.makeText(ChangePassword.this, "this ID do not exist", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ChangePassword.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}