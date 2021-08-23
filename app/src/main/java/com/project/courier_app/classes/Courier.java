package com.project.courier_app.classes;

public class Courier  {

    private String email, primaryPhone ,secondaryPhone, password, firstName, lastName, id,token,vehicle,currentDelivery;
    private String role = "COURIER";

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public String getCurrentDelivery() {
        return currentDelivery;
    }

  /*  public void setCurrentDelivery(String currentDelivery) {
        this.currentDelivery = currentDelivery;
    }*/

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

    public String getRole() {
        return role;
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
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
    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }
}


