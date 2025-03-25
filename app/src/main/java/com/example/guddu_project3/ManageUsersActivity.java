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

public class ManageUsersActivity extends AppCompatActivity {

    private ListView lvUsers;
    private DBHelper dbHelper;
    private ArrayList<String> userList;
    private ArrayList<Integer> userIds;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        lvUsers = findViewById(R.id.lvUsers);
        dbHelper = new DBHelper(this);

        loadUsers();

        // Set a long click listener to delete a user
        lvUsers.setOnItemLongClickListener((parent, view, position, id) -> {
            int userId = userIds.get(position);
            new AlertDialog.Builder(ManageUsersActivity.this)
                    .setTitle("Delete User")
                    .setMessage("Are you sure you want to delete this user?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        int rowsDeleted = dbHelper.deleteUser(userId);
                        if (rowsDeleted > 0) {
                            Toast.makeText(ManageUsersActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                            loadUsers(); // Refresh the list
                        } else {
                            Toast.makeText(ManageUsersActivity.this, "Deletion failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    private void loadUsers() {
        userList = new ArrayList<>();
        userIds = new ArrayList<>();

        Cursor cursor = dbHelper.getAllUsers();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EMAIL));
                String role = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ROLE));

                String detail = "Name: " + name + "\nEmail: " + email + "\nRole: " + role;
                userList.add(detail);
                userIds.add(id);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(this, "No users found", Toast.LENGTH_SHORT).show();
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        lvUsers.setAdapter(adapter);
    }
}
