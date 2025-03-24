package com.example.guddu_project3;


public class Hotel {
    private String name;
    private String location;
    private String rating;
    private String price;

    public Hotel(String name, String location, String rating, String price) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public String getRating() {
        return rating;
    }
    public String getPrice() {
        return price;
    }
}
