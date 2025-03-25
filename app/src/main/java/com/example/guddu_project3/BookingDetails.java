package com.example.guddu_project3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class BookingDetails extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etCheckInDate, etCheckOutDate;
    private Button btnSubmitBooking;
    private ImageView ivCall, ivFacebook, ivTwitter, ivWhatsapp;

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

        // Set up date pickers for check-in and check-out date fields
        etCheckInDate.setOnClickListener(v -> showDatePickerDialog(etCheckInDate));
        etCheckOutDate.setOnClickListener(v -> showDatePickerDialog(etCheckOutDate));

        // Set up the "BOOK NOW / PAY" button click listener
        btnSubmitBooking.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String checkIn = etCheckInDate.getText().toString().trim();
            String checkOut = etCheckOutDate.getText().toString().trim();

            // Basic validation of inputs
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
                Toast.makeText(BookingDetails.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Implement your booking logic here (e.g., send data to a server or show confirmation)
                Toast.makeText(BookingDetails.this, "Booking Submitted", Toast.LENGTH_SHORT).show();
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
