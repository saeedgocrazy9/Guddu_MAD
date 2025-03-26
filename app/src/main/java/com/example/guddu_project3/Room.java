package com.example.guddu_project3;

public class Room {
    private int id;
    private String roomNumber;
    private String roomType;
    private String roomPrice;
    private String status;
    private int imageResId;

    public Room(int id, String roomNumber, String roomType, String roomPrice, String status, int imageResId) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.status = status;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public String getStatus() {
        return status;
    }

    public int getImageResId() {
        return imageResId;
    }
}
