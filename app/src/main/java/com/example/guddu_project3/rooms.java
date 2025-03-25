package com.example.guddu_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class rooms extends AppCompatActivity {

    private Button btnLuxury, btnBasic, btnStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        btnLuxury = findViewById(R.id.btnLuxuryRooms);
        btnBasic = findViewById(R.id.btnBasicRooms);
        btnStandard = findViewById(R.id.btnStandardRooms);

        btnLuxury.setOnClickListener(view -> openManageRooms("LUXURY"));
        btnBasic.setOnClickListener(view -> openManageRooms("BASIC"));
        btnStandard.setOnClickListener(view -> openManageRooms("STANDARD"));
    }

    private void openManageRooms(String roomType) {
        Intent intent = new Intent(rooms.this, ManageRoomsActivity.class);
        intent.putExtra("ROOM_TYPE", roomType);
        startActivity(intent);
    }
}
