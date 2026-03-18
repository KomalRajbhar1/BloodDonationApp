package com.example.blooddonationapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    TextView greeting;

    CardView btnAddDonor, btnViewDonor, btnSettings, btnViewRequests, requestBlood, bloodGuide, bloodBank, Statistics;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAddDonor = findViewById(R.id.cardAddDonor);
        btnViewDonor = findViewById(R.id.cardViewDonor);
        btnSettings= findViewById(R.id.cardSettings);
        requestBlood=findViewById(R.id.cardRequestBlood);
        bloodGuide=findViewById(R.id.cardBloodGuide);
        bloodBank=findViewById(R.id.cardBloodBank);
        Statistics=findViewById(R.id.cardStatistics);
        greeting=findViewById(R.id.txtGreeting);
        btnViewRequests=findViewById(R.id.cardViewRequests);

        SharedPreferences preferences=getSharedPreferences("UserSession", MODE_PRIVATE);
        String name=preferences.getString("username", "User");
        greeting.setText("Hello, "+name+  " 👋");

        btnAddDonor.setOnClickListener(v ->
                startActivity(new Intent(this, AddDonorActivity.class)));

        btnViewDonor.setOnClickListener(v ->
                startActivity(new Intent(this, DonorListActivity.class)));

        btnSettings.setOnClickListener(v->startActivity(new Intent(this, SettingsActivity.class)));

        requestBlood.setOnClickListener(v->{
            Intent intent=new Intent(DashboardActivity.this,RequestBloodActivity.class);
            startActivity(intent);
        });
        bloodGuide.setOnClickListener(v ->{
            Intent intent=new Intent(DashboardActivity.this, BloodGuideActivity.class);
            startActivity(intent);
        });
        bloodBank.setOnClickListener(v ->{
            Intent intent=new Intent(DashboardActivity.this, BloodBankActivity.class);
            startActivity(intent);
        });
        Statistics.setOnClickListener(v ->{
            Intent intent=new Intent(DashboardActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });
        btnViewRequests.setOnClickListener(v -> {
            startActivity(new Intent(this, RequestListActivity.class));
        });
    }
}