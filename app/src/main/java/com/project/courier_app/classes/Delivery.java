package com.project.courier_app.classes;
// noting changed, completely copied from Business app

public class Delivery
{

    private Address destAddress;
    private String clientPhone;
    private String clientName;
    private String Note;
    private String Time;
    private String deliveredDate;
    private double price;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    private String status;

    public void setCurrentDelivery(String currentDelivery) {
        this.currentDelivery = currentDelivery;
    }

    private String currentDelivery;
    public void setPrice(double price) {
        this.price = price;
    }

    private String id;

    @Override
    public String toString() {
        return  " client name: "+clientName+ "\n client phone: "+clientPhone+"\n ADDRESS- city:"+destAddress.getCity()+
                "\n apartment: "+destAddress.getApartment()+"\n number: "+destAddress.getNumber()+"\n price: "+price+ "\n note: "+Note;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Delivery(Address clientAddress, String clientPhone, String clientName, String clientNote, String time, String date,String status) {
        this.destAddress= clientAddress;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.Time = time;
        this.Note = clientNote;
        this.deliveredDate = date;
        this.status=status;

    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Address getClientAddress() {
        return destAddress;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDate() {
        return deliveredDate;
    }

    public void setDate(String date) {
        this.deliveredDate = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public void setClientAddress(Address clientAddress) {
        this.destAddress = clientAddress;
    }
}


