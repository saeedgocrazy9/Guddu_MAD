package com.example.guddu_project3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class activiyt_hotel_room extends AppCompatActivity {

    private TextView tvHotelRoomTitle;
private int countOfRoom=0;
    private TextView tvTotalAvailableRooms;
    private LinearLayout roomsContainer;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_room);
 tvTotalAvailableRooms=findViewById(R.id.tvTotalAvailableRooms);
        tvHotelRoomTitle = findViewById(R.id.tvHotelRoomTitle);
        roomsContainer = findViewById(R.id.roomsContainer);
        dbHelper = new DBHelper(this);

        countOfRoom=dbHelper.getAvailableRoomsCount();
        tvTotalAvailableRooms.setText("Total Available Rooms: " + countOfRoom);
        // Retrieve the static city/hotel name passed via intent
        String hotelName = getIntent().getStringExtra("HOTEL_NAME");
        if (hotelName == null || hotelName.trim().isEmpty()) {
            hotelName = "Unknown Hotel";
        }
        tvHotelRoomTitle.setText("Rooms at " + hotelName);

        // Load room details from the database and display them dynamically
        loadRoomsFromDB();
    }

    private void loadRoomsFromDB() {
        // Clear any existing views in the container
        roomsContainer.removeAllViews();

        // Get all rooms from the database
        Cursor cursor = dbHelper.getAllRooms();
        if (cursor != null && cursor.moveToFirst()) {
            LayoutInflater inflater = LayoutInflater.from(this);
            do {
                // Extract room details from the cursor
                int roomId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ROOM_ID));
                String roomNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ROOM_NUMBER));
                String roomType = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ROOM_TYPE));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STATUS));

                // Create a Room object (using our updated Room model)
                Room room = new Room(roomId, roomNumber, roomType, "₹" + price + " per night", status, getImageResId(roomType));

                // Inflate the room card layout
                View roomCard = inflater.inflate(R.layout.room_card, roomsContainer, false);

                // Bind the room data to the card views
                ImageView ivRoomImage = roomCard.findViewById(R.id.ivRoomImage);
                TextView tvRoomName = roomCard.findViewById(R.id.tvRoomName);
                TextView tvRoomType = roomCard.findViewById(R.id.tvRoomType);
                TextView tvRoomPrice = roomCard.findViewById(R.id.tvRoomPrice);

                ivRoomImage.setImageResource(room.getImageResId());
                tvRoomName.setText("Room #" + room.getRoomNumber());
                tvRoomType.setText(room.getRoomType());
                tvRoomPrice.setText(room.getRoomPrice() + " | " + room.getStatus());

                // Set click listener to open the room booking page or show message if occupied
                roomCard.setOnClickListener(v -> {
                    if (room.getStatus().equalsIgnoreCase("occupied")) {
                        // Query the bookings table for this room's checkout date
                        Cursor bookingCursor = dbHelper.getBookingForRoom(room.getId());
                        if (bookingCursor != null && bookingCursor.moveToFirst()) {
                            String checkoutDate = bookingCursor.getString(bookingCursor.getColumnIndexOrThrow(DBHelper.COLUMN_CHECK_OUT_DATE));
                            Toast.makeText(activiyt_hotel_room.this,
                                    "Room is occupied till " + checkoutDate,
                                    Toast.LENGTH_SHORT).show();
                            bookingCursor.close();
                        } else {
                            Toast.makeText(activiyt_hotel_room.this,
                                    "Room is occupied",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Room is available, open the BookingDetails activity
                        Intent intent = new Intent(activiyt_hotel_room.this, BookingDetails.class);
                        intent.putExtra("ROOM_ID", room.getId());
                        intent.putExtra("ROOM_NAME", room.getRoomNumber());
                        intent.putExtra("ROOM_TYPE", room.getRoomType());
                        intent.putExtra("ROOM_PRICE", room.getRoomPrice());
                        intent.putExtra("ROOM_IMAGE", room.getImageResId());
                        startActivity(intent);
                    }
                });

                // Add the room card to the container
                roomsContainer.addView(roomCard);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(this, "No rooms found in database.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Returns a static image resource based on the room type.
     * Adjust the mapping as needed.
     */
    private int getImageResId(String roomType) {
        if (roomType == null) return R.drawable.room1;
        switch (roomType.toLowerCase(Locale.ROOT)) {
            case "deluxe":
                return R.drawable.room2;  // replace with your actual drawable resource
            case "superior":
                return R.drawable.room4; // replace with your actual drawable resource
            case "suite":
                return R.drawable.room3;    // replace with your actual drawable resource
            case "standard":
                return R.drawable.room5; // replace with your actual drawable resource
            case "executive":
                return R.drawable.room6; // replace with your actual drawable resource
            case "premium":
                return R.drawable.room7;  // replace with your actual drawable resource
            default:
                return R.drawable.room8;  // default image resource
        }
    }
}
