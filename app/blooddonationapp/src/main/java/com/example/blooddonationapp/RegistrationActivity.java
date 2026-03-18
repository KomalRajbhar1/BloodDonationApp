package com.example.blooddonationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Database.DatabaseHelper;

public class RegistrationActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnRegister;
    DatabaseHelper db;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Register");
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        db = new DatabaseHelper(this);

        btnRegister.setOnClickListener(v -> {

            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(username.isEmpty()){
                etUsername.setError("Enter username");
                return;
            }
            else if(password.length() < 8){
                etPassword.setError("Password must be at least 8 characters");
                return;
            }

            boolean inserted = db.registerUser(username, password);

            if(inserted){
                Toast.makeText(this,"Registration Successful",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
