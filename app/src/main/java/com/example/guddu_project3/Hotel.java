package com.example.guddu_project3;

public class Hotel {
    private String name;
    private String location;
    private String rating;
    private String price;
    private int imageResId; // Drawable resource for the hotel image

    public Hotel(String name, String location, String rating, String price, int imageResId) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.price = price;
        this.imageResId = imageResId;
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

    public int getImageResId() {
        return imageResId;
    }
}
