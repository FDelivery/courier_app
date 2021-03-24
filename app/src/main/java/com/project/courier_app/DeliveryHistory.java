package com.project.courier_app;

// in here the courier can see a list of his on done delivers
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DeliveryHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_history);
    }
}