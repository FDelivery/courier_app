package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChooseDelivery extends AppCompatActivity {
    private ListView listView;
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();
    String CourierUser,ID,TOKEN,deliveryID;
    public static Activity a;
    private Socket mSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_delivery);
        listView=(ListView) findViewById(R.id.listDelivery);
        a=this;
        mSocket = SocketIO.getSocket();
        mSocket.on("delivery_accepted_for_courier", (msg)-> {
            GetDeliveries();

        });
        mSocket.on("delivery_posted", (msg)->GetDeliveries());
        Courier courier;
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            CourierUser = extras.getString("CourierUserInGson");
            ID =extras.getString("id");
            TOKEN=extras.getString("token");
            courier = new Gson().fromJson(CourierUser, Courier.class);
            GetDeliveries();
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mSocket.off("delivery_accepted");
        mSocket.off("delivery_accepted_for_courier");
    }

    private void GetDeliveries() //this give us all deliveries with status "COURIER_SEARCHING"
    {
        Call<List<String>> call = rtfBase.getDeliveries("COURIER_SEARCHING");
        ArrayList<String> arrayList = new ArrayList<>();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response)
            {

                if(response.code() == 400)
                {
                    Toast.makeText(ChooseDelivery.this, "this ID do not exist",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 200)
                {

                    for(int i=0;i<response.body().size();i++) {
                        deliveryID=response.body().get(i).substring(18,42);

                        Delivery delivery = new Gson().fromJson(response.body().get(i), Delivery.class);
                        delivery.setId(deliveryID);
                        arrayList.add(delivery.getClientName()+" "+delivery.getClientPhone()+"\nid="+deliveryID);

                    }
                    help(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(ChooseDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void help(ArrayList<String> arrayList){
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetDelivery(arrayList.get(position).split("id=")[1]);




            }
        });
    }
    private void GetDelivery(String idDelivery) //put delivery id and this return you the delivery
    {
        Call<String> call = rtfBase.getDelivery(idDelivery);
        Intent intent=new Intent(this, showChoosenDelivery.class);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 400)
                {
                    Toast.makeText(ChooseDelivery.this, "this ID do not exist",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 200)
                {

                    Delivery GSON = new Gson().fromJson(response.body(),Delivery.class);
                    intent.putExtra("token",TOKEN);
                    intent.putExtra("delivery",response.body());
                    intent.putExtra("idDelivery",idDelivery);
                    intent.putExtra("id",ID);
                    intent.putExtra("CourierUserInGson",CourierUser);

                    startActivity(intent);




                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ChooseDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

}