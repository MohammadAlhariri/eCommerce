package com.example.ma_ecommerce.model;

public class Users {

    private String name,password,address,image,email,answer1,answer2;
private int phone,ID;

    public Users(String name, String password, String address, String image, String email, String answer1, String answer2, int phone, int ID) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.image = image;
        this.email = email;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.phone = phone;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Users(String name, String password, String address, String image, String email, int phone, int ID) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.image = image;
        this.email = email;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.phone = phone;
        this.ID = ID;
    }
}
