package com.project.courier_app.classes;

public class Courier  {

    private String email, primaryPhone ,secondaryPhone, password, firstName, lastName, id,token,vehicle,currentDelivery;
    private String role = "COURIER";

    public Courier(String email, String primaryPhone, String secondaryPhone, String password, String firstName, String lastName, String vehicle) {
        this.email = email;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicle = vehicle;
        this.currentDelivery="None";
    }


    public Courier(String email, String primaryPhone, String password, String firstName, String lastName, String vehicle) {
        this.email = email;
        this.primaryPhone = primaryPhone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicle = vehicle;
        this.currentDelivery="None";

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getEmail() { return email; }

    public String getPassword() {
        return password;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getToken() {
        return token;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public String getCurrentDelivery() {
        return currentDelivery;
    }
}


