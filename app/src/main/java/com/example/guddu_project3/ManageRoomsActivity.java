package com.example.guddu_project3;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ManageRoomsActivity extends AppCompatActivity {

    private TextView tvRoomTypeTitle;
    private ListView listViewRooms;
    private Button btnAddRoom;

    private DBHelper dbHelper;
    private String roomType; // e.g., "LUXURY", "BASIC", "STANDARD"

    // We'll store room IDs and display text in parallel lists or a custom class
    private List<Integer> roomIds = new ArrayList<>();
    private List<String> roomDisplayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms);

        // Bind UI
        tvRoomTypeTitle = findViewById(R.id.tvRoomTypeTitle);
        listViewRooms = findViewById(R.id.listViewRooms);
        btnAddRoom = findViewById(R.id.btnAddRoom);

        // DB Helper
        dbHelper = new DBHelper(this);

        // Get room type from intent
        roomType = getIntent().getStringExtra("ROOM_TYPE");
        if (roomType == null) {
            roomType = "BASIC"; // Default fallback
        }

        // Set title
        tvRoomTypeTitle.setText("Manage " + roomType + " Rooms");

        // Setup list adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomDisplayList);
        listViewRooms.setAdapter(adapter);

        // Load rooms from DB
        loadRoomsByType(roomType);

        // Add room button
        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open AddEditRoomActivity in add mode
                Intent intent = new Intent(ManageRoomsActivity.this, AddEditRoomActivity.class);
                intent.putExtra("ROOM_TYPE", roomType);
                startActivity(intent);
            }
        });

        // On item click -> edit
        listViewRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int roomId = roomIds.get(position);
                // Open AddEditRoomActivity in edit mode
                Intent intent = new Intent(ManageRoomsActivity.this, AddEditRoomActivity.class);
                intent.putExtra("ROOM_ID", roomId);
                startActivity(intent);
            }
        });

        // On item long click -> delete
        listViewRooms.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int roomId = roomIds.get(position);

                new AlertDialog.Builder(ManageRoomsActivity.this)
                        .setTitle("Delete Room")
                        .setMessage("Are you sure you want to delete this room?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int rowsDeleted = dbHelper.deleteRoom(roomId);
                                if (rowsDeleted > 0) {
                                    Toast.makeText(ManageRoomsActivity.this, "Room deleted", Toast.LENGTH_SHORT).show();
                                    loadRoomsByType(roomType); // reload
                                } else {
                                    Toast.makeText(ManageRoomsActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload rooms in case any were added/updated
        loadRoomsByType(roomType);
    }

    private void loadRoomsByType(String type) {
        roomIds.clear();
        roomDisplayList.clear();

        Cursor cursor = dbHelper.getRoomsByType(type); // Make sure you have getRoomsByType(String type) in DBHelper
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ROOM_ID));
                String roomNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ROOM_NUMBER));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STATUS));

                // Store in parallel lists
                roomIds.add(id);
                roomDisplayList.add("Room #" + roomNumber + " | $" + price + " | " + status);

            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }
}
