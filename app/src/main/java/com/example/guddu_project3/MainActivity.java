package com.example.guddu_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Example in HomeActivity.java
        Button btnSearch = findViewById(R.id.btnSearch);
        EditText etDestination = findViewById(R.id.etDestination);

        btnSearch.setOnClickListener(v -> {
            String destination = etDestination.getText().toString().trim();
            Intent intent = new Intent(MainActivity.this, activity_destination.class);
            intent.putExtra("DESTINATION_NAME", destination);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_home) {
                // Already in MainActivity; do nothing or reload content
                return true;
            } else if (id == R.id.menu_about) {
                startActivity(new Intent(MainActivity.this, About.class));
                return true;
            } else if (id == R.id.menu_account) {
                startActivity(new Intent(MainActivity.this, Account_page.class));
                return true;
            }
            return false;
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}