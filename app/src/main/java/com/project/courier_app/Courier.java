package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Courier extends AppCompatActivity {

    private String email, primaryPhone ,secondaryPhone, password, firstName, lastName, id,token,vehicle,role = "COURIER";

    public Courier(String email, String primaryPhone, String secondaryPhone, String password, String firstName, String lastName, String vehicle) {
        this.email = email;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicle = vehicle;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Courier(String email, String primaryPhone, String password, String firstName, String lastName, String vehicle) {
        this.email = email;
        this.primaryPhone = primaryPhone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicle = vehicle;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getEmail() { return email; }
    //In my opinion(sarah) you do not need it because in my opinion you should not allow the business / courier to change the email with which they registered
    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }
}


