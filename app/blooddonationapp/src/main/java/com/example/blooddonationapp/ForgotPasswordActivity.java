package com.example.blooddonationapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Database.DatabaseHelper;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etUsername, etNewPassword;
    Button btnReset;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Reset Password");
        }

        etUsername = findViewById(R.id.etUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnReset = findViewById(R.id.btnReset);

        db = new DatabaseHelper(this);

        btnReset.setOnClickListener(v -> {

            String username = etUsername.getText().toString();
            String newPassword = etNewPassword.getText().toString();

            if(username.isEmpty() || newPassword.isEmpty()){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
                return;
            }
            boolean updated = db.updatePassword(username, newPassword);

            if(updated){
                Toast.makeText(this,"Password Reset Successful",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this,"User not found",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}