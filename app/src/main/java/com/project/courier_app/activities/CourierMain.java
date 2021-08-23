package com.project.courier_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.courier_app.R;
import com.project.courier_app.classes.Courier;
import com.project.courier_app.classes.Delivery;
import com.project.courier_app.classes.RetrofitBase;
import com.project.courier_app.classes.RetrofitInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourierMain extends AppCompatActivity {
    private ImageButton deliveryHistory;
    private ImageButton chooseDeliveryFromList;
    private ImageButton activeDelivery;
    private ImageButton myProfile;
    private TextView welcome;
    private RetrofitInterface rtfBase;
    Courier courier;
    Intent intent;
    String CourierUser,ID,TOKEN,deliveryID;
    Delivery delivery;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_main);
        welcome=findViewById(R.id.textViewWelcom);
        deliveryHistory=findViewById(R.id.deliveryHistory);
        chooseDeliveryFromList=findViewById(R.id.chooseDelivery);
        activeDelivery=findViewById(R.id.deliveryList);
        myProfile=findViewById(R.id.myprofile);
        rtfBase = RetrofitBase.getRetrofitInterface();


        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            CourierUser = extras.getString("CourierUserInGson");
            courier=new Gson().fromJson(CourierUser, Courier.class);
            ID =extras.getString("id");
            TOKEN=extras.getString(("token"));
            welcome.setText("welcome "+courier.getFirstName());

        }

        //show the info of active delivery and option to change status (delivered,exception..)
        activeDelivery.setOnClickListener((v) -> {
            intent= new Intent(this, com.project.courier_app.activities.activeDelivery.class);
            intent.putExtra("token",TOKEN);
            intent.putExtra("id",ID);

            startActivity(intent);
        });
//show profile info and option to change
        myProfile.setOnClickListener((v) -> {

            intent =new Intent(this,CourierProfile.class);
            intent.putExtra("CourierUserInGson",CourierUser);
            intent.putExtra("token",TOKEN);
            intent.putExtra("id",ID);
            startActivity(intent);
        });


//this give us all deliveries that this courier delivered
        deliveryHistory.setOnClickListener((v) -> {
            getHistory();
        });


        //show us all deliveries and we can choose one
        chooseDeliveryFromList.setOnClickListener((v) -> {
            chooseDelivery();
        });
    }





    //show all deliveries that sends. First we will check if there are such deliveries
    private void getHistory() {

        Call<List<String>> call = rtfBase.getDeliveriesHistory("DELIVERED",ID);
        ArrayList<String> arrayListShow=new ArrayList<>();
        ArrayList<String> arrayListId=new ArrayList<>();
        Intent intent =new Intent(this, DeliveryHistory.class);
        Bundle bundle = new Bundle();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response)
            {


                if(response.code() == 200)
                {
                    for(int i=0;i<response.body().size();i++) {
                        delivery = new Gson().fromJson(response.body().get(i), Delivery.class);
                        if(delivery.getStatus().equals("DELIVERED")) {
                            deliveryID = response.body().get(i).substring(18, 42);
                            arrayListShow.add("Client Name: "+delivery.getClientName() + "\nNumber: " + delivery.getClientPhone());
                            arrayListId.add(deliveryID);
                        }
                    }
                    if (!arrayListShow.isEmpty()) {
                        intent.putExtra("CourierUserInGson",CourierUser);
                        intent.putExtra("token",TOKEN);
                        intent.putExtra("id",ID);
                        bundle.putSerializable("arrayListShow",(Serializable)arrayListShow);
                        bundle.putSerializable("arrayListId",(Serializable)arrayListId);
                        intent.putExtra("BUNDLE",bundle);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(CourierMain.this, "You have not made any deliveries yet",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(CourierMain.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }
    //this give us all deliveries with status "COURIER_SEARCHING" and we can choose one
    private void chooseDelivery() {
        Call<List<String>> call = rtfBase.getDeliveries("COURIER_SEARCHING");
        ArrayList<String> arrayListShow=new ArrayList<>();
        ArrayList<String> arrayListId=new ArrayList<>();
        Intent intent =new Intent(this, ChooseDelivery.class);
        Bundle bundle = new Bundle();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response)
            {

                if(response.code() == 200)
                {

                    for(int i=0;i<response.body().size();i++) {
                        delivery = new Gson().fromJson(response.body().get(i), Delivery.class);
                        if(delivery.getStatus().equals("COURIER_SEARCHING")) {
                            deliveryID = response.body().get(i).substring(18, 42);
                            arrayListShow.add("Client Name: "+delivery.getClientName() + "\nNumber: " + delivery.getClientPhone());
                            arrayListId.add(deliveryID);
                        }
                    }

                    if (!arrayListShow.isEmpty()) {
                        intent.putExtra("token",TOKEN);
                        intent.putExtra("CourierUserInGson",CourierUser);
                        intent.putExtra("id",ID);
                        bundle.putSerializable("arrayListShow",(Serializable)arrayListShow);
                        bundle.putSerializable("arrayListId",(Serializable)arrayListId);
                        intent.putExtra("BUNDLE",bundle);
                        startActivity(intent);
                    }

                }
                else{
                    Toast.makeText(CourierMain.this, "we have no waiting deliveries",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(CourierMain.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    }

}