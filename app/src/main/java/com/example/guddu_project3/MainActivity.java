package com.example.guddu_project3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Get views from layout
        Button btnSearch = findViewById(R.id.btnSearch);
        EditText etDestination = findViewById(R.id.etDestination);

        // Launch the destination activity on button click
        btnSearch.setOnClickListener(v -> {
            String destination = etDestination.getText().toString().trim();
            // Ensure you are using the correct class name
            Intent intent = new Intent(MainActivity.this, activity_destination.class);
            intent.putExtra("DESTINATION_NAME", destination);
            startActivity(intent);
        });

        // Adjust padding based on system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
