package com.example.guddu_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class activity_destination extends AppCompatActivity {

    private TextView tvDestinationTitle;
    private LinearLayout hotelsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        tvDestinationTitle = findViewById(R.id.tvDestinationTitle);
        hotelsContainer = findViewById(R.id.hotelsContainer);

        // Retrieve the destination from the home page (passed via intent)
        String destination = getIntent().getStringExtra("DESTINATION_NAME");
        if (destination == null || destination.trim().isEmpty()) {
            destination = "Unknown Destination";
        }
        tvDestinationTitle.setText("Hotels in " + destination);

        // Retrieve exactly 4 hotels for the given destination
        List<Hotel> hotels = getHotelsForDestination(destination);

        // Inflate and add hotel cards to the container
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Hotel hotel : hotels) {
            View hotelCard = inflater.inflate(R.layout.hotel_card, hotelsContainer, false);
            TextView tvHotelName = hotelCard.findViewById(R.id.tvHotelName);
            TextView tvHotelLocation = hotelCard.findViewById(R.id.tvHotelLocation);
            TextView tvHotelRating = hotelCard.findViewById(R.id.tvHotelRating);
            TextView tvHotelPrice = hotelCard.findViewById(R.id.tvHotelPrice);

            tvHotelName.setText(hotel.getName());
            tvHotelLocation.setText(hotel.getLocation());
            tvHotelRating.setText(hotel.getRating());
            tvHotelPrice.setText(hotel.getPrice());

            // Optionally, set the image resource for the hotel if available.
            // ImageView ivHotelImage = hotelCard.findViewById(R.id.ivHotelImage);
            // ivHotelImage.setImageResource(hotel.getImageResId());

            hotelsContainer.addView(hotelCard);
        }
    }

    // Method that returns exactly 4 hotels for each destination
    private List<Hotel> getHotelsForDestination(String destination) {
        List<Hotel> hotels = new ArrayList<>();
        switch (destination.toLowerCase()) {
            case "mumbai":
                hotels.add(new Hotel("Oberoi Hotel Mumbai", "Mumbai", "⭐ 8.9 Very Good", "₹12,000 per night"));
                hotels.add(new Hotel("Taj Hotel Mumbai", "Mumbai", "⭐ 9.2 Excellent", "₹15,500 per night"));
                hotels.add(new Hotel("Trident Hotel Mumbai", "Mumbai", "⭐ 9.0 Excellent", "₹14,000 per night"));
                hotels.add(new Hotel("The Leela Mumbai", "Mumbai", "⭐ 8.8 Very Good", "₹13,500 per night"));
                break;
            case "delhi":
                hotels.add(new Hotel("The Imperial", "Delhi", "⭐ 9.0 Excellent", "₹11,000 per night"));
                hotels.add(new Hotel("ITC Maurya", "Delhi", "⭐ 9.1 Excellent", "₹12,500 per night"));
                hotels.add(new Hotel("Le Meridien", "Delhi", "⭐ 8.7 Very Good", "₹10,500 per night"));
                hotels.add(new Hotel("Hyatt Regency", "Delhi", "⭐ 8.9 Very Good", "₹11,500 per night"));
                break;
            case "punjab":
                hotels.add(new Hotel("Taj Lake Palace", "Punjab", "⭐ 8.5 Very Good", "₹10,500 per night"));
                hotels.add(new Hotel("Hyatt Regency", "Punjab", "⭐ 8.7 Very Good", "₹11,000 per night"));
                hotels.add(new Hotel("Raddison Blu", "Punjab", "⭐ 8.8 Very Good", "₹10,800 per night"));
                hotels.add(new Hotel("The Oberoi", "Punjab", "⭐ 9.0 Excellent", "₹12,000 per night"));
                break;
            case "uttarakhand":
                hotels.add(new Hotel("Chamunda Retreat", "Uttarakhand", "⭐ 8.7 Very Good", "₹9,500 per night"));
                hotels.add(new Hotel("The Grand Oak", "Uttarakhand", "⭐ 8.9 Very Good", "₹10,000 per night"));
                hotels.add(new Hotel("Elysian Valley", "Uttarakhand", "⭐ 8.6 Very Good", "₹9,000 per night"));
                hotels.add(new Hotel("Mount View Inn", "Uttarakhand", "⭐ 8.8 Very Good", "₹9,800 per night"));
                break;
            case "bangalore":
                hotels.add(new Hotel("The Leela Palace", "Bangalore", "⭐ 9.1 Excellent", "₹13,000 per night"));
                hotels.add(new Hotel("Taj West End", "Bangalore", "⭐ 9.0 Excellent", "₹12,500 per night"));
                hotels.add(new Hotel("ITC Gardenia", "Bangalore", "⭐ 8.8 Very Good", "₹11,500 per night"));
                hotels.add(new Hotel("The Ritz-Carlton", "Bangalore", "⭐ 9.2 Excellent", "₹14,000 per night"));
                break;
            case "pune":
                hotels.add(new Hotel("JW Marriott Pune", "Pune", "⭐ 9.0 Excellent", "₹12,500 per night"));
                hotels.add(new Hotel("Conrad Pune", "Pune", "⭐ 8.9 Very Good", "₹11,500 per night"));
                hotels.add(new Hotel("Marriott Suites Pune", "Pune", "⭐ 8.8 Very Good", "₹11,000 per night"));
                hotels.add(new Hotel("Oberoi Pune", "Pune", "⭐ 9.1 Excellent", "₹13,000 per night"));
                break;
            default:
                // For any other destination, you may choose to return an empty list or a default set of hotels.
                hotels.add(new Hotel("Default Hotel 1", destination, "⭐ 8.0 Good", "₹10,000 per night"));
                hotels.add(new Hotel("Default Hotel 2", destination, "⭐ 8.0 Good", "₹10,000 per night"));
                hotels.add(new Hotel("Default Hotel 3", destination, "⭐ 8.0 Good", "₹10,000 per night"));
                hotels.add(new Hotel("Default Hotel 4", destination, "⭐ 8.0 Good", "₹10,000 per night"));
                break;
        }
        return hotels;
    }
}
