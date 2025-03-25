package com.example.guddu_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class activiyt_hotel_room extends AppCompatActivity {

    private TextView tvHotelRoomTitle;
    private Button btnBack;
    private LinearLayout roomsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_room);

        tvHotelRoomTitle = findViewById(R.id.tvHotelRoomTitle);
        btnBack = findViewById(R.id.btnBack);
        roomsContainer = findViewById(R.id.roomsContainer);

        // Retrieve the hotel name passed via intent
        String hotelName = getIntent().getStringExtra("HOTEL_NAME");
        if (hotelName == null || hotelName.trim().isEmpty()) {
            hotelName = "Unknown Hotel";
        }
        tvHotelRoomTitle.setText("Rooms at " + hotelName);

        // Back button to finish the activity
        btnBack.setOnClickListener(v -> finish());

        // Retrieve room details for the hotel (4 rooms, each with a different image)
        List<Room> rooms = getRoomsForHotel(hotelName);

        // Inflate room cards and add them to the container
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Room room : rooms) {
            View roomCard = inflater.inflate(R.layout.room_card, roomsContainer, false);

            ImageView ivRoomImage = roomCard.findViewById(R.id.ivRoomImage);
            TextView tvRoomName = roomCard.findViewById(R.id.tvRoomName);
            TextView tvRoomType = roomCard.findViewById(R.id.tvRoomType);
            TextView tvRoomPrice = roomCard.findViewById(R.id.tvRoomPrice);

            ivRoomImage.setImageResource(room.getImageResId());
            tvRoomName.setText(room.getRoomName());
            tvRoomType.setText(room.getRoomType());
            tvRoomPrice.setText(room.getRoomPrice());

            // Set click listener to open the room booking page
            roomCard.setOnClickListener(v -> {
                Intent intent = new Intent(activiyt_hotel_room.this, BookingDetails.class);
                intent.putExtra("ROOM_NAME", room.getRoomName());
                intent.putExtra("ROOM_TYPE", room.getRoomType());
                intent.putExtra("ROOM_PRICE", room.getRoomPrice());
                intent.putExtra("ROOM_IMAGE", room.getImageResId());
                startActivity(intent);
            });

            roomsContainer.addView(roomCard);
        }
    }

    // Returns 4 room cards for the given hotel, each with different images.
    private List<Room> getRoomsForHotel(String hotelName) {
        List<Room> rooms = new ArrayList<>();
        String lowerName = hotelName.toLowerCase();
        if (lowerName.contains("mumbai")) {
            rooms.add(new Room(hotelName + " Deluxe Room", "Deluxe", "₹3,500 per night", R.drawable.room1));
            rooms.add(new Room(hotelName + " Superior Room", "Superior", "₹2,800 per night", R.drawable.room2));
            rooms.add(new Room(hotelName + " Executive Suite", "Suite", "₹5,000 per night", R.drawable.room3));
            rooms.add(new Room(hotelName + " Standard Room", "Standard", "₹2,000 per night", R.drawable.room4));
        } else if (lowerName.contains("delhi")) {
            rooms.add(new Room(hotelName + " Presidential Suite", "Suite", "₹8,000 per night", R.drawable.room5));
            rooms.add(new Room(hotelName + " Executive Room", "Executive", "₹4,500 per night", R.drawable.room6));
            rooms.add(new Room(hotelName + " Deluxe Room", "Deluxe", "₹3,800 per night", R.drawable.room7));
            rooms.add(new Room(hotelName + " Standard Room", "Standard", "₹2,200 per night", R.drawable.room8));
        } else if (lowerName.contains("pune")) {
            rooms.add(new Room(hotelName + " Suite", "Suite", "₹6,000 per night", R.drawable.room9));
            rooms.add(new Room(hotelName + " Premium Room", "Premium", "₹4,000 per night", R.drawable.room10));
            rooms.add(new Room(hotelName + " Standard Room", "Standard", "₹2,500 per night", R.drawable.room11));
            rooms.add(new Room(hotelName + " Superior Room", "Superior", "₹3,200 per night", R.drawable.room12));
        } else if (lowerName.contains("uttarakhand")) {
            rooms.add(new Room(hotelName + " Mountain View Suite", "Suite", "₹7,000 per night", R.drawable.room13));
            rooms.add(new Room(hotelName + " Cozy Deluxe Room", "Deluxe", "₹3,500 per night", R.drawable.room14));
            rooms.add(new Room(hotelName + " Family Room", "Family", "₹4,200 per night", R.drawable.room15));
            rooms.add(new Room(hotelName + " Standard Room", "Standard", "₹2,800 per night", R.drawable.room16));
        } else {
            // Default rooms if hotel doesn't match above cases.
            rooms.add(new Room(hotelName + " Deluxe Room", "Deluxe", "₹3,500 per night", R.drawable.room1));
            rooms.add(new Room(hotelName + " Superior Room", "Superior", "₹2,800 per night", R.drawable.room2));
            rooms.add(new Room(hotelName + " Executive Suite", "Suite", "₹5,000 per night", R.drawable.room3));
            rooms.add(new Room(hotelName + " Standard Room", "Standard", "₹2,000 per night", R.drawable.room4));
        }
        return rooms;
    }
}
