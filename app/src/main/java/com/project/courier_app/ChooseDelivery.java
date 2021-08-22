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
    String CourierUser,ID,TOKEN;
    public static Activity a;
    private Socket mSocket;
    ArrayList<String> arrayListShow,arraylistId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_delivery);
        listView=(ListView) findViewById(R.id.listDelivery);
        a=this;

        Bundle extras = getIntent().getExtras();
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        arrayListShow = (ArrayList<String>) args.getSerializable("arrayListShow");
        arraylistId = (ArrayList<String>) args.getSerializable("arrayListId");
        mSocket = SocketIO.getSocket();
        mSocket.on("delivery_accepted_for_courier", (msg)-> {
            helpArrayAdapter(arrayListShow);

        });
        mSocket.on("delivery_posted", (msg)->helpArrayAdapter(arrayListShow));





        if(extras!=null)
        {
            CourierUser = extras.getString("CourierUserInGson");
            ID =extras.getString("id");
            TOKEN=extras.getString("token");

            helpArrayAdapter(arrayListShow);
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


    //we put the arraylist in adapter
    private void helpArrayAdapter(ArrayList<String> arrayList){
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetDelivery(arraylistId.get(position));




            }
        });
    }


    //put delivery id and this return you the delivery info
    private void GetDelivery(String idDelivery)
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