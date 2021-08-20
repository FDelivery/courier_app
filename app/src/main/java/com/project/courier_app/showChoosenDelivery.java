package com.project.courier_app;


// in here the courier can see delivers and choose one
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class showChoosenDelivery extends AppCompatActivity {
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();
    private TextView deliveriInfo;
    private Button Choose;

    String TOKEN;
    String DELIVERY,IDDELIVERY,ID,CourierUser;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_choosen);
        deliveriInfo = findViewById(R.id.printall);
        Choose=findViewById(R.id.choose);



        Delivery delivery;
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {

            ID =extras.getString("id");
            IDDELIVERY=extras.getString("idDelivery");
            DELIVERY =extras.getString("delivery");
            delivery=new Gson().fromJson(DELIVERY, Delivery.class) ;
            deliveriInfo.setText("ID: "+IDDELIVERY+"\n" +delivery.toString());
            TOKEN=extras.getString("token");


        }

//choose a deliver for sending it
        Choose.setOnClickListener((v)->{
            takeDeliver();

        });
        
    }




//update the delivery status
  private void takeDeliver(){
      HashMap<String, String> statusMap = new HashMap<String, String>();
      statusMap.put("status","COURIER_ACCEPTED");
      Call<Void> call= rtfBase.registerDelivery("Bearer "+TOKEN,IDDELIVERY,statusMap);
      call.enqueue(new Callback<Void>() {
          @Override
          public void onResponse(Call<Void> call, Response<Void> response) {

              if(response.code() == 200 ||response.code() == 204) {


                  ChooseDelivery.a.finish();
                  finish();

                  updateUser();

              }
              else{
                  Toast.makeText(showChoosenDelivery.this, "delivery id not exist",Toast.LENGTH_LONG).show();

              }
          }

          @Override
          public void onFailure(Call<Void> call, Throwable t) {
              Toast.makeText(showChoosenDelivery.this,t.getMessage(),Toast.LENGTH_LONG).show();

          }
      });



  }

  //we need to update in user info the id-delivery
  private void updateUser(){
      HashMap<String, String> map=new HashMap<>();
      map.put("currentDelivery",IDDELIVERY);
      Call<Void> call2= rtfBase.updateUser(ID,map);
      call2.enqueue(new Callback<Void>() {
          @Override
          public void onResponse(Call<Void> call, Response<Void> response) {

              if(response.code()==200){
                  Toast.makeText(showChoosenDelivery.this, "Successfully selected",Toast.LENGTH_LONG).show();

                  help();
              }else{
                  Toast.makeText(showChoosenDelivery.this, "user id not exist",Toast.LENGTH_LONG).show();

              }
          }

          @Override
          public void onFailure(Call<Void> call, Throwable t) {
              Toast.makeText(showChoosenDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

          }
      });

  }

//pull the delivery and user after updates info
    private void help(){
        Call<String> call3= rtfBase.getUser(ID);
        call3.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200){

                    CourierUser=response.body();

                }else{
                    Toast.makeText(showChoosenDelivery.this, "user id not exist",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(showChoosenDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        Call<String> call4= rtfBase.getDelivery(IDDELIVERY);
        call4.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200){

                    DELIVERY =response.body();

                }else{
                    Toast.makeText(showChoosenDelivery.this, "delivery id not exist",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(showChoosenDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}