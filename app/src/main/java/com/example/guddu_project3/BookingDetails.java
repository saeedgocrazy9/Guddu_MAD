package com.example.guddu_project3;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingDetails extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etCheckInDate, etCheckOutDate;
    private Button btnSubmitBooking;
    private ImageView ivCall, ivFacebook, ivTwitter, ivWhatsapp;
    private DBHelper dbHelper;
    private int roomId; // Passed from previous activity
    // For demonstration, we'll use a fixed user ID.
    private int userId = 1;

    // Define the date format to be used
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_details);

        // Initialize views from the XML layout
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etCheckInDate = findViewById(R.id.etCheckInDate);
        etCheckOutDate = findViewById(R.id.etCheckOutDate);
        btnSubmitBooking = findViewById(R.id.btnSubmitBooking);

        ivCall = findViewById(R.id.ivCall);
        ivFacebook = findViewById(R.id.ivFacebook);
        ivTwitter = findViewById(R.id.ivTwitter);
        ivWhatsapp = findViewById(R.id.ivWhatsapp);

        dbHelper = new DBHelper(this);

        // Set up date pickers for check-in and check-out date fields
        etCheckInDate.setOnClickListener(v -> showDatePickerDialog(etCheckInDate));
        etCheckOutDate.setOnClickListener(v -> showDatePickerDialog(etCheckOutDate));

        // Get the room ID passed via intent (ensure you pass this from the previous activity)
        roomId = getIntent().getIntExtra("ROOM_ID", -1);
        if (roomId == -1) {
            Toast.makeText(this, "Room not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Set up the booking submission button listener
        btnSubmitBooking.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String checkIn = etCheckInDate.getText().toString().trim();
            String checkOut = etCheckOutDate.getText().toString().trim();

            // Basic validation of inputs
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
                Toast.makeText(BookingDetails.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate that checkout date is after checkin date
            try {
                Date checkInDate = dateFormat.parse(checkIn);
                Date checkOutDate = dateFormat.parse(checkOut);

                if (checkOutDate == null || checkInDate == null || !checkOutDate.after(checkInDate)) {
                    Toast.makeText(BookingDetails.this, "Checkout date must be after checkin date", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(BookingDetails.this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert booking record and update room status
            boolean success = dbHelper.bookRoom(userId, roomId, checkIn, checkOut);
            if (success) {
                Toast.makeText(BookingDetails.this, "Booking Submitted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(BookingDetails.this, "Booking Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Shows a DatePickerDialog and sets the chosen date into the provided EditText
    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                BookingDetails.this,
                (view, year, month, dayOfMonth) -> {
                    // Note: month is zero-indexed
                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    editText.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
