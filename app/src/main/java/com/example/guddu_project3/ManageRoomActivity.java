package com.example.guddu_project3;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ManageRoomActivity extends AppCompatActivity {

    private RecyclerView rvRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable EdgeToEdge mode for a full screen experience
        EdgeToEdge.enable(this);

        // Set the content view to your activity_manage_room.xml layout
        setContentView(R.layout.activity_manage_room);

        // Ensure the root view in your activity_manage_room.xml has the id "main"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Apply system window insets as padding to the view
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Find the RecyclerView
        rvRooms = findViewById(R.id.rvRooms);

        // 2. Set a layout manager
        rvRooms.setLayoutManager(new LinearLayoutManager(this));

        // 3. Prepare some dummy data (Room objects)
       // List<Room> roomList = new ArrayList<>();
      //  roomList.add(new Room("101", "Single", 50, "Available"));
      //  roomList.add(new Room("102", "Double", 80, "Booked"));
      //  roomList.add(new Room("103", "Suite", 120, "Available"));
      //  roomList.add(new Room("104", "Double", 75, "Available"));
      //  roomList.add(new Room("105", "Single", 60, "Booked"));
//
        // 4. Create and set the adapter
      //  RoomAdapter adapter = new RoomAdapter(roomList);
     //   rvRooms.setAdapter(adapter);
    }
}
