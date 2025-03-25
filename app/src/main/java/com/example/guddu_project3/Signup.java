package com.example.guddu_project3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    private EditText etName, etSignupEmail, etSignupPassword, etConfirmPassword;
    private RadioGroup radioGroupSignupUserType;
    private Button btnSignup;
    private DBHelper dbHelper; // your DBHelper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etSignupEmail = findViewById(R.id.etSignupEmail);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        radioGroupSignupUserType = findViewById(R.id.radioGroupSignupUserType);
        btnSignup = findViewById(R.id.btnSignup);

        dbHelper = new DBHelper(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupUser();
            }
        });
    }

    private void signupUser() {
        String name = etName.getText().toString().trim();
        String email = etSignupEmail.getText().toString().trim();
        String password = etSignupPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(Signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected role from radio group
        int selectedId = radioGroupSignupUserType.getCheckedRadioButtonId();
        RadioButton selectedRoleButton = findViewById(selectedId);
        String role = selectedRoleButton.getText().toString().toLowerCase(); // "user" or "admin"

        // Check if user already exists (optional)
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + DBHelper.TABLE_USERS + " WHERE " + DBHelper.COLUMN_EMAIL + "=?",
                new String[]{email});
        if(cursor != null && cursor.getCount() > 0){
            Toast.makeText(Signup.this, "Email already registered", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }
        if(cursor != null){
            cursor.close();
        }

        // Insert new user
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USER_NAME, name);
        values.put(DBHelper.COLUMN_EMAIL, email);
        values.put(DBHelper.COLUMN_PASSWORD, password); // Remember to hash in production!
        values.put(DBHelper.COLUMN_ROLE, role);

        long result = dbHelper.getWritableDatabase().insert(DBHelper.TABLE_USERS, null, values);
        if(result != -1){
            Toast.makeText(Signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
            // Optionally redirect to login page
            startActivity(new Intent(Signup.this, Login.class));
            finish();
        } else {
            Toast.makeText(Signup.this, "Signup Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
