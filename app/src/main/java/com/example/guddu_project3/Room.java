package com.example.guddu_project3;

public class Room {
    private String roomName;
    private String roomType;
    private String roomPrice;
    private int imageResId;

    public Room(String roomName, String roomType, String roomPrice, int imageResId) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.imageResId = imageResId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public int getImageResId() {
        return imageResId;
    }
}
