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
    private String status;
    private String currentDelivery;
    private String id;


    public Delivery(Address clientAddress, String clientPhone, String clientName, String clientNote, String time, String date,String status) {
        this.destAddress= clientAddress;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.Time = time;
        this.Note = clientNote;
        this.deliveredDate = date;
        this.status=status;

    }
    public Delivery(String Id, Address clientAddress, String clientPhone, String clientName, String clientNote, String time, String date,String status) {
        this.destAddress= clientAddress;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.Time = time;
        this.Note = clientNote;
        this.deliveredDate = date;
        this.status=status;
        this.id = Id;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getClientName() {
        return clientName;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return  " client name: "+clientName+ "\n client phone: "+clientPhone+"\n ADDRESS- city:"+destAddress.getCity()+
                "\n apartment: "+destAddress.getApartment()+"\n number: "+destAddress.getNumber()+"\n price: "+price+ "\n note: "+Note;
    }
}


