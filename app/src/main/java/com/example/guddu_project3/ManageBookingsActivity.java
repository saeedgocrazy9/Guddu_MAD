package com.example.guddu_project3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManageBookingsActivity extends AppCompatActivity {

    private ListView lvBookings;
    private DBHelper dbHelper;
    private ArrayList<String> bookingList;
    private ArrayList<Integer> bookingIds;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bookings);

        lvBookings = findViewById(R.id.lvBookings);
        dbHelper = new DBHelper(this);

        loadBookings();

        // Set long click listener to cancel a booking
        lvBookings.setOnItemLongClickListener((parent, view, position, id) -> {
            int bookingId = bookingIds.get(position);
            new AlertDialog.Builder(ManageBookingsActivity.this)
                    .setTitle("Cancel Booking")
                    .setMessage("Do you want to cancel this booking?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        int rowsAffected = dbHelper.cancelBooking(bookingId);
                        if (rowsAffected > 0) {
                            Toast.makeText(ManageBookingsActivity.this, "Booking cancelled", Toast.LENGTH_SHORT).show();
                            loadBookings(); // Refresh the list
                        } else {
                            Toast.makeText(ManageBookingsActivity.this, "Failed to cancel booking", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    private void loadBookings() {
        bookingList = new ArrayList<>();
        bookingIds = new ArrayList<>();

        Cursor cursor = dbHelper.getAllBookings();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int bookingId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BOOKING_ID));
                int roomId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BOOKING_ROOM_ID));
                String checkIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CHECK_IN_DATE));
                String checkOut = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CHECK_OUT_DATE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BOOKING_STATUS));

                String detail = "Booking ID: " + bookingId +
                        "\nRoom ID: " + roomId +
                        "\nCheck-In: " + checkIn +
                        "\nCheck-Out: " + checkOut +
                        "\nStatus: " + status;
                bookingList.add(detail);
                bookingIds.add(bookingId);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(this, "No bookings found", Toast.LENGTH_SHORT).show();
        }

adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,bookingList);
        lvBookings.setAdapter(adapter);
    }
}
