package com.example.hw_laptopshop0105;

import java.io.Serializable;

public class Laptop implements Serializable {
    private String id,name;
    private int price;
    private byte[] image;

    public Laptop(String id, String name, int price, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Laptop() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
