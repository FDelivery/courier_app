package com.project.courier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Courier extends AppCompatActivity {

    private String email, FirstName, PhoneNumber, LastName, Password;


    Courier(String e, String p, String lastname, String firstname, String password)
    {
        email = e;
        PhoneNumber = p;
        LastName = lastname;
        FirstName = firstname;
    }

    public String getEmail() { return email; }
    //In my opinion(sarah) you do not need it because in my opinion you should not allow the business / courier to change the email with which they registered
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return FirstName; }

    public void setFirstName(String businessName) { FirstName = businessName; }

    public String getPhoneNumber() { return PhoneNumber; }

    public void setPhoneNumber1(String phoneNumber) { PhoneNumber = phoneNumber; }

    public String getLastName() { return LastName; }

    public void setLastName(String address) { LastName = address; }

    public String getPassword() { return Password; }
    public void deletePassword(){ Password = "";}
}


