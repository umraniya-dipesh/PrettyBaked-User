package com.example.prettybakeduser.Model;

public class city {
    String City;
    int img;

    public city(String city, int img) {
        City = city;
        this.img = img;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
