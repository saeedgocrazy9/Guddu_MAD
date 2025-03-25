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

public class activity_destination extends AppCompatActivity {

    private TextView tvDestinationTitle;
    private LinearLayout hotelsContainer;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        // Initialize views from activity_destination.xml
        tvDestinationTitle = findViewById(R.id.tvDestinationTitle);
        hotelsContainer = findViewById(R.id.hotelsContainer);
        btnBack = findViewById(R.id.btnBack);

        // Back button returns to the previous activity
        btnBack.setOnClickListener(v -> finish());

        // Retrieve the destination passed via intent
        String destination = getIntent().getStringExtra("DESTINATION_NAME");
        if (destination == null || destination.trim().isEmpty()) {
            destination = "Unknown Destination";
        }
        tvDestinationTitle.setText("Hotels in " + destination);

        // Get the list of hotels for the given destination
        List<Hotel> hotels = getHotelsForDestination(destination);

        // Inflate hotel cards and add them to the container
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Hotel hotel : hotels) {
            View hotelCard = inflater.inflate(R.layout.hotel_card, hotelsContainer, false);

            // Get views from the hotel card layout
            ImageView ivHotelImage = hotelCard.findViewById(R.id.ivHotelImage);
            TextView tvHotelName = hotelCard.findViewById(R.id.tvHotelName);
            TextView tvHotelLocation = hotelCard.findViewById(R.id.tvHotelLocation);
            TextView tvHotelRating = hotelCard.findViewById(R.id.tvHotelRating);
            TextView tvHotelPrice = hotelCard.findViewById(R.id.tvHotelPrice);

            // Set the hotel data
            ivHotelImage.setImageResource(hotel.getImageResId());
            tvHotelName.setText(hotel.getName());
            tvHotelLocation.setText(hotel.getLocation());
            tvHotelRating.setText(hotel.getRating());
            tvHotelPrice.setText(hotel.getPrice());

            // Set click listener to open the room page for this hotel
            hotelCard.setOnClickListener(v -> {
                Intent intent = new Intent(activity_destination.this, activiyt_hotel_room.class);
                intent.putExtra("HOTEL_NAME", hotel.getName());
                startActivity(intent);
            });

            hotelsContainer.addView(hotelCard);
        }
    }

    // Returns a list of hotels for the given destination
    private List<Hotel> getHotelsForDestination(String destination) {
        List<Hotel> hotels = new ArrayList<>();
        switch (destination.toLowerCase()) {
            case "mumbai":
                hotels.add(new Hotel("Oberoi Hotel Mumbai", "Mumbai", "⭐ 8.9 Very Good", "₹12,000 per night", R.drawable.hotel2));
                hotels.add(new Hotel("Taj Hotel Mumbai", "Mumbai", "⭐ 9.2 Excellent", "₹15,500 per night", R.drawable.hotel3));
                hotels.add(new Hotel("Trident Hotel Mumbai", "Mumbai", "⭐ 9.0 Excellent", "₹14,000 per night", R.drawable.hotel4));
                hotels.add(new Hotel("The Leela Mumbai", "Mumbai", "⭐ 8.8 Very Good", "₹13,500 per night", R.drawable.hotel5));
                break;
            case "delhi":
                hotels.add(new Hotel("The Imperial", "Delhi", "⭐ 9.0 Excellent", "₹11,000 per night", R.drawable.delhi1));
                hotels.add(new Hotel("ITC Maurya", "Delhi", "⭐ 9.1 Excellent", "₹12,500 per night", R.drawable.delhi2));
                hotels.add(new Hotel("Le Meridien", "Delhi", "⭐ 8.7 Very Good", "₹10,500 per night", R.drawable.delhi3));
                hotels.add(new Hotel("Hyatt Regency", "Delhi", "⭐ 8.9 Very Good", "₹11,500 per night", R.drawable.delhi4));
                break;
            case "pune":
                hotels.add(new Hotel("JW Marriott Pune", "Pune", "⭐ 9.0 Excellent", "₹12,500 per night", R.drawable.photel1));
                hotels.add(new Hotel("Conrad Pune", "Pune", "⭐ 8.9 Very Good", "₹11,500 per night", R.drawable.photel2));
                hotels.add(new Hotel("Marriott Suites Pune", "Pune", "⭐ 8.8 Very Good", "₹11,000 per night", R.drawable.photel3));
                hotels.add(new Hotel("Oberoi Pune", "Pune", "⭐ 9.1 Excellent", "₹13,000 per night", R.drawable.photel4));
                break;
            case "uttarakhand":
                hotels.add(new Hotel("Chamunda Retreat", "Uttarakhand", "⭐ 8.7 Very Good", "₹9,500 per night", R.drawable.uhotel1));
                hotels.add(new Hotel("The Grand Oak", "Uttarakhand", "⭐ 8.9 Very Good", "₹10,000 per night", R.drawable.uhotel2));
                hotels.add(new Hotel("Elysian Valley", "Uttarakhand", "⭐ 8.6 Very Good", "₹9,000 per night", R.drawable.uhotel3));
                hotels.add(new Hotel("Mount View Inn", "Uttarakhand", "⭐ 8.8 Very Good", "₹9,800 per night", R.drawable.uhotel4));
                break;
            default:
                hotels.add(new Hotel("Default Hotel 1", destination, "⭐ 8.0 Good", "₹10,000 per night", R.drawable.hotel2));
                hotels.add(new Hotel("Default Hotel 2", destination, "⭐ 8.0 Good", "₹10,000 per night", R.drawable.photel4));
                hotels.add(new Hotel("Default Hotel 3", destination, "⭐ 8.0 Good", "₹10,000 per night", R.drawable.delhi3));
                hotels.add(new Hotel("Default Hotel 4", destination, "⭐ 8.0 Good", "₹10,000 per night", R.drawable.uhotel4));
                break;
        }
        return hotels;
    }
}
