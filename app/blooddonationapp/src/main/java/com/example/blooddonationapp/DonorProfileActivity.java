package com.example.blooddonationapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Database.DatabaseHelper;
import com.example.blooddonationapp.Model.Person;

import java.util.List;

public class DonorProfileActivity extends AppCompatActivity {

    TextView name,blood,phone,location;
    android.widget.Button btnEdit;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Donor Profile");
        }
        db = new DatabaseHelper(this);

        name = findViewById(R.id.tvName);
        blood = findViewById(R.id.tvBlood);
        phone = findViewById(R.id.tvPhone);
        location = findViewById(R.id.tvLocation);
        btnEdit = findViewById(R.id.btnEdit);

        Intent intent = getIntent();

        int donorId = intent.getIntExtra("id", -1);
        String donorName = intent.getStringExtra("name");
        String donorBlood = intent.getStringExtra("blood");
        String donorPhone = intent.getStringExtra("phone");
        String donorLocation = intent.getStringExtra("location");

        name.setText("Name: " + donorName);
        blood.setText("Blood Group: " + donorBlood);
        phone.setText("Phone: " + donorPhone);
        location.setText("Location: " + donorLocation);

        btnEdit.setOnClickListener(v ->{

            Intent editIntent = new Intent(DonorProfileActivity.this, UpdateActivity.class);

            editIntent.putExtra("id", donorId);
            editIntent.putExtra("name", donorName);
            editIntent.putExtra("blood", donorBlood);
            editIntent.putExtra("phone", donorPhone);
            editIntent.putExtra("area", donorLocation);

            startActivityForResult(editIntent, 1);
        });
    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();

        List<Person> donorList = db.getAllDonors();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            finish(); // close profile → go back to list
        }
    }
}
