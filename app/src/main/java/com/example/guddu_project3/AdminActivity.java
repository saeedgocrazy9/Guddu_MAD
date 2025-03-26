package com.example.guddu_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private TextView tvAdminTitle;
    private TextView tvTotalRooms;
    private TextView tvTotalBookings;
    private TextView tvTotalUsers;
    private Button btnManageRooms;
    private Button btnManageBookings;
    private Button btnManageUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admiin); // Ensure the correct layout file is set

        // Initialize views
        tvAdminTitle = findViewById(R.id.tvAdminTitle);
        tvTotalRooms = findViewById(R.id.tvTotalRooms);
        tvTotalBookings = findViewById(R.id.tvTotalBookings);
        tvTotalUsers = findViewById(R.id.tvTotalUsers);
        btnManageRooms = findViewById(R.id.btnManageRooms);
        btnManageBookings = findViewById(R.id.btnManageBookings);
        btnManageUsers = findViewById(R.id.btnManageUsers);

        // Set admin panel title
        tvAdminTitle.setText("🏨 Hotel Admin Panel");

        // Initialize database helper
        DBHelper dbHelper = new DBHelper(this);

        // Fetch data from database
        int totalRooms = dbHelper.getTotalRooms();
        int totalBookings = dbHelper.getTotalBookings();
        int totalUsers = dbHelper.getTotalUsers();

        // Set data to TextViews
        tvTotalRooms.setText(String.valueOf(totalRooms));
        tvTotalBookings.setText(String.valueOf(totalBookings));
        tvTotalUsers.setText(String.valueOf(totalUsers));

        // Set onClick listeners
        btnManageRooms.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, rooms.class);
            startActivity(intent);
        });

        btnManageBookings.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, ManageBookingsActivity.class);
            startActivity(intent);
        });

        btnManageUsers.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, ManageUsersActivity.class);
            startActivity(intent);
        });
    }
}
