package com.example.blooddonationapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Database.DatabaseHelper;

public class StatisticsActivity extends AppCompatActivity {

    TextView total, totalRequests;
    TextView aplus, aminus, bplus, bminus, oplus, ominus, abplus, abminus;

    DatabaseHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Statistics");
        }

        // ✅ Initialize views
        total = findViewById(R.id.tvTotal);
        totalRequests = findViewById(R.id.tvTotalRequests);

        aplus = findViewById(R.id.tvAplus);
        aminus = findViewById(R.id.tvAminus);

        bplus = findViewById(R.id.tvBplus);
        bminus = findViewById(R.id.tvBminus);

        oplus = findViewById(R.id.tvOplus);
        ominus = findViewById(R.id.tvOminus);

        abplus = findViewById(R.id.tvABplus);
        abminus = findViewById(R.id.tvABminus);

        db = new DatabaseHelper(this);

        // ✅ Total donors
        int totalDonors = db.getTotalDonors();
        total.setText("Total Donors: " + totalDonors);

        // ✅ Total requests (make sure method exists)
        int requests = db.getTotalRequests();
        totalRequests.setText("Total Requests: " + requests);

        // ✅ Blood group counts (ALL types)
        aplus.setText("A+ : " + db.getBloodGroupCount("A+"));
        aminus.setText("A- : " + db.getBloodGroupCount("A-"));

        bplus.setText("B+ : " + db.getBloodGroupCount("B+"));
        bminus.setText("B- : " + db.getBloodGroupCount("B-"));

        oplus.setText("O+ : " + db.getBloodGroupCount("O+"));
        ominus.setText("O- : " + db.getBloodGroupCount("O-"));

        abplus.setText("AB+ : " + db.getBloodGroupCount("AB+"));
        abminus.setText("AB- : " + db.getBloodGroupCount("AB-"));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}