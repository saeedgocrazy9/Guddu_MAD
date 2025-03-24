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
        super.onCreate(savedInstanceState);        // Enable EdgeToEdge mode for a full screen experience
        EdgeToEdge.enable(this);

        // Set the content view to your activity_manage_room.xml layout
        setContentView(R.layout.activity_manage_room);

        // Ensure the root view in your activity_manage_room.xml has the id "main"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.manageRoom), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Apply system window insets as padding to the view
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}
