package com.example.ma_ecommerce.model;

public class Orders {
    private String address,city,date,phone,name,totalAmount,orderID;

    public Orders() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Orders(String address, String city, String date, String phone, String name, String totalAmount, String orderID) {
        this.address = address;
        this.city = city;
        this.date = date;
        this.phone = phone;
        this.name = name;
        this.totalAmount = totalAmount;
        this.orderID = orderID;
    }

    public Orders(String address, String city, String date, String phone, String name, String totalAmount) {
        this.address = address;
        this.city = city;
        this.date = date;

        this.phone = phone;
        this.name = name;
        this.totalAmount = totalAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
