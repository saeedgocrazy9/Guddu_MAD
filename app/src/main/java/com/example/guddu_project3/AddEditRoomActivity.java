package com.example.guddu_project3;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditRoomActivity extends AppCompatActivity {

    private TextView tvAddEditTitle;
    private EditText etRoomNumber, etPrice, etStatus;
    private DBHelper dbHelper;

    private int roomId = -1;      // If we get a ROOM_ID, we are editing
    private String roomType = ""; // If we get a ROOM_TYPE, we know which type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_room);

        tvAddEditTitle = findViewById(R.id.tvAddEditTitle);
        etRoomNumber = findViewById(R.id.etRoomNumber);
        etPrice = findViewById(R.id.etPrice);
        etStatus = findViewById(R.id.etStatus);

        dbHelper = new DBHelper(this);

        // Check if we have ROOM_ID
        if (getIntent().hasExtra("ROOM_ID")) {
            roomId = getIntent().getIntExtra("ROOM_ID", -1);
            loadRoomData(roomId);
            tvAddEditTitle.setText("Edit Room");
        }
        // Otherwise, we might have ROOM_TYPE
        else if (getIntent().hasExtra("ROOM_TYPE")) {
            roomType = getIntent().getStringExtra("ROOM_TYPE");
            tvAddEditTitle.setText("Add " + roomType + " Room");
        } else {
            // Fallback
            roomType = "BASIC";
            tvAddEditTitle.setText("Add Room");
        }

        // Save button
        findViewById(R.id.btnSaveRoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRoom();
            }
        });
    }

    private void loadRoomData(int id) {
        Cursor cursor = dbHelper.getRoomById(id);
        if (cursor != null && cursor.moveToFirst()) {
            String roomNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ROOM_NUMBER));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STATUS));
            roomType = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ROOM_TYPE));

            etRoomNumber.setText(roomNumber);
            etPrice.setText(String.valueOf(price));
            etStatus.setText(status);

            cursor.close();
        }
    }

    private void saveRoom() {
        String roomNumber = etRoomNumber.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String status = etStatus.getText().toString().trim();

        if (TextUtils.isEmpty(roomNumber) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(status)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

        if (roomId == -1) {
            // Adding a new room
            long newId = dbHelper.addRoom(roomNumber, roomType, price);
            if (newId != -1) {
                // Also update status (since addRoom doesn't include status by default)
                dbHelper.updateRoom((int) newId, roomNumber, roomType, price, status);
                Toast.makeText(this, "Room added successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add room", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Updating an existing room
            int rows = dbHelper.updateRoom(roomId, roomNumber, roomType, price, status);
            if (rows > 0) {
                Toast.makeText(this, "Room updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        }

        finish(); // Go back to ManageRoomsActivity
    }
}
