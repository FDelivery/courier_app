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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryTable extends AppCompatActivity {
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();
    private TextView printall;
    private Button chooseDel;
String TOKEN;
    String deliveryFromIntent,IDDELIVERY,ID,FromIntent;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_tablee);
        printall = (TextView)findViewById(R.id.printall);
        chooseDel=(Button)findViewById(R.id.choose);


        Courier courier;
        Delivery delivery;
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {

            FromIntent = extras.getString("CourierUserInGson");
            courier=new Gson().fromJson(FromIntent, Courier.class);
            ID =extras.getString("id");
            IDDELIVERY=extras.getString("idDelivery");
            deliveryFromIntent=extras.getString("delivery");
            delivery=new Gson().fromJson(deliveryFromIntent, Delivery.class) ;
            printall.setText("ID: "+IDDELIVERY+"\n" +delivery.toString());
            TOKEN=extras.getString(("token"));


        }


        chooseDel.setOnClickListener((v)->{

            Log.i("aaaaaaaa",TOKEN+" aaa");

            Call<Void> call= rtfBase.registerDelivery("Bearer "+TOKEN,IDDELIVERY,"COURIER_ACCEPTED");
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if(response.code()==200){




                        Toast.makeText(DeliveryTable.this, "good",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(DeliveryTable.this, "something not good",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(DeliveryTable.this, "something not good"+t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        finish();
        });
        
    }
}