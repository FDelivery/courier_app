package com.project.courier_app;


// in here the courier can see delivers and choose one
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    private TextView printall;
    private Button Choose;
    Courier courier;
String TOKEN;
    String deliveryFromIntent,IDDELIVERY,ID,CourierUser;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_choosen);
        printall = (TextView)findViewById(R.id.printall);
        Choose=(Button)findViewById(R.id.choose);



        Delivery delivery;
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {

           // CourierUser = extras.getString("CourierUserInGson");
          //  courier=new Gson().fromJson(CourierUser, Courier.class);
            ID =extras.getString("id");
            IDDELIVERY=extras.getString("idDelivery");
            deliveryFromIntent=extras.getString("delivery");
            delivery=new Gson().fromJson(deliveryFromIntent, Delivery.class) ;
            printall.setText("ID: "+IDDELIVERY+"\n" +delivery.toString());
            TOKEN=extras.getString("token");


        }


        Choose.setOnClickListener((v)->{
            Intent intent =new Intent(this, CourierMain.class);
            Log.i("wewewe1","token="+TOKEN);
            Log.i("wewewe1","IDDELIVERY="+IDDELIVERY);
            Call<Void> call= rtfBase.registerDelivery("Bearer "+TOKEN,IDDELIVERY,"COURIER_ACCEPTED");
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if(response.code() == 200 ||response.code() == 204){

                        Log.i("wewewe1","????????5??????");
                        Toast.makeText(showChoosenDelivery.this, "good",Toast.LENGTH_LONG).show();


                    }else{
                        Toast.makeText(showChoosenDelivery.this, "something not good",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(showChoosenDelivery.this, "something not good"+t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
            Log.i("wewewe1","del="+IDDELIVERY);
            Log.i("wewewe1","id="+ID);

            HashMap<String, String> map=new HashMap<>();
            map.put("currentDelivery",IDDELIVERY);
            Call<Void> call2= rtfBase.updateUser(ID,map);
            call2.enqueue(new Callback<Void>() {
               @Override
               public void onResponse(Call<Void> call, Response<Void> response) {

                   if(response.code()==200){

                       Log.i("wewewe1","????????3??????");
                         help();
                   }else{
                       Toast.makeText(showChoosenDelivery.this, "something not good-updateUser",Toast.LENGTH_LONG).show();

                   }
              }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Toast.makeText(showChoosenDelivery.this, "something not good"+t.getMessage(),Toast.LENGTH_LONG).show();

    }
});

        finish();
            intent.putExtra("CourierUserInGson",CourierUser);
            Log.i("wewewe1",CourierUser);
            intent.putExtra("id",ID);
            intent.putExtra("token",TOKEN);
            intent.putExtra("delivery",deliveryFromIntent);
            Log.i("wewewe2",deliveryFromIntent);


            startActivity(intent);
        });
        
    }


    private void help(){
        Call<String> call3= rtfBase.getUser(ID);
        call3.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200){
                    Log.i("wewewe1","????????1??????");

                    CourierUser=response.body();

                }else{
                    Toast.makeText(showChoosenDelivery.this, "something not good",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(showChoosenDelivery.this, "something not good-getUser",Toast.LENGTH_LONG).show();

            }
        });

        Call<String> call4= rtfBase.getDelivery(IDDELIVERY);
        call4.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200){
                    Log.i("wewewe1","????????2??????");

                    deliveryFromIntent=response.body();

                }else{
                    Toast.makeText(showChoosenDelivery.this, "something not good",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(showChoosenDelivery.this, "something not good-getDelivery",Toast.LENGTH_LONG).show();

            }
        });

    }
}