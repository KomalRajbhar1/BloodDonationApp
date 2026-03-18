package com.example.blooddonationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

            finish();

        }, 2500); // ⏱️ 2.5 seconds splash
    }
}