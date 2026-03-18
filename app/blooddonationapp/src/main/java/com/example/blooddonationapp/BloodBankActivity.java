package com.example.blooddonationapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BloodBankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Blood Banks List");
        }
    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
