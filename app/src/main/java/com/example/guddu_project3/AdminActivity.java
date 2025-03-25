package com.example.guddu_project3; // Replace with your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private TextView tvAdminTitle;
    private TextView tvTotalRooms;
    private Button btnManageRooms;
    private Button btnManageBookings;
    private Button btnManageUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to your admin.xml layout
        setContentView(R.layout.admiin);

        tvTotalRooms=findViewById(R.id.tvTotalRooms);
        // Initialize views
        tvAdminTitle = findViewById(R.id.tvAdminTitle);
        btnManageRooms = findViewById(R.id.btnManageRooms);
        btnManageBookings = findViewById(R.id.btnManageBookings);
        btnManageUsers = findViewById(R.id.btnManageUsers);

        // Optionally, set the title programmatically (or it can remain set in the XML)
        tvAdminTitle.setText("🏨 Hotel Admin Panel");
        DBHelper dbHelper = new DBHelper(this);

        // Get total rooms and set it to the TextView
        int totalRooms = dbHelper.getTotalRooms();
        tvTotalRooms.setText("Total Rooms: " + totalRooms);
        // Set onClick listeners for buttons

        // When the "Manage Rooms" button is clicked, launch ManageRoomActivity
        btnManageRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, rooms.class);
                startActivity(intent);
            }
        });

        // When the "Manage Bookings" button is clicked, launch ManageBookingsActivity
        btnManageBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(AdminActivity.this, ManageBookingsActivity.class);
//                startActivity(intent);
            }
        });

        // When the "Manage Users" button is clicked, launch ManageUsersActivity
        btnManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(AdminActivity.this, ManageUsersActivity.class);
//                startActivity(intent);
            }
        });
    }
}
