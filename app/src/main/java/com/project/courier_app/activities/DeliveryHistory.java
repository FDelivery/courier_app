package com.project.courier_app.activities;

// in here the courier can see a list of his on done delivers
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.courier_app.R;
import com.project.courier_app.classes.Courier;
import com.project.courier_app.classes.RetrofitBase;
import com.project.courier_app.classes.RetrofitInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryHistory extends AppCompatActivity {

    private RetrofitInterface rtfBase;
    Courier courier;
    String CourierUser,ID,TOKEN;
    ListView listView;
    ArrayList<String> arrayListShow,arraylistId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_history);

        rtfBase = RetrofitBase.getRetrofitInterface();
        listView=(ListView) findViewById(R.id.listDeliveryHistory);


        Bundle extras = getIntent().getExtras();
        Bundle args = getIntent().getBundleExtra("BUNDLE");


        arrayListShow = (ArrayList<String>) args.getSerializable("arrayListShow");
        arraylistId = (ArrayList<String>) args.getSerializable("arrayListId");

        if(extras!=null) {
            CourierUser = extras.getString("CourierUserInGson");
            courier = new Gson().fromJson(CourierUser, Courier.class);
            ID = extras.getString("id");
            TOKEN = extras.getString(("token"));
            helpArrayAdapter(arrayListShow);

        }
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
    //open activity with delivery info
    private void GetDelivery(String idDelivery)
    {
        Call<String> call = rtfBase.getDelivery(idDelivery);
        Intent intent=new Intent(this,showChosenFromHistory.class);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 400)
                {
                    Toast.makeText(DeliveryHistory.this, "this ID do not exist",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 200)
                {

                    intent.putExtra("delivery",response.body());
                    intent.putExtra("idDelivery",idDelivery);
                    intent.putExtra("token",TOKEN);
                    intent.putExtra("id",ID);

                    startActivity(intent);




                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DeliveryHistory.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

}