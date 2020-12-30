package com.example.ma_ecommerce.model;

public class Products {
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String category, description,name,image,date, productState;
private double price;
private int pid,quantity;
    public Products() {
    }

    public Products(String name,  double price, int pid, int quantity,String description,String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.pid = pid;
        this.quantity = quantity;
        this.image = image;
    }

    public Products(String description, String name, double price, String image, int pid, String date, String category, String productState) {
        this.description = description;
        this.name = name;
        this.price = price;
        this.image = image;
        this.pid = pid;
        this.date = date;
        this.category=category;
        this.productState = productState;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPid() {
        return pid;
    }

    @Override
    public String toString() {
        return "Products{" +
                "category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", date='" + date + '\'' +
                ", productState='" + productState + '\'' +
                ", price=" + price +
                ", pid=" + pid +
                '}';
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
