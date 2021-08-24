package com.project.courier_app.classes;

public class Address {


    private String city,street,number,apartment,floor,entrance;


    public Address(String city, String street, String number, String apartment, String floor, String entrance) {
        this.city = city;
        this.street = street;
        this.floor = floor;
        this.number = number;
        this.apartment = apartment;
        this.entrance = entrance;
    }


    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }

    public String getApartment() {
        return apartment;
    }
    

}
