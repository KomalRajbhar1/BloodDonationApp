package com.example.blooddonationapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BloodGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blood_guide);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Blood Guide");
        }
    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
