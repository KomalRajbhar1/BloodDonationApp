package com.example.blooddonationapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Database.DatabaseHelper;

public class StatisticsActivity extends AppCompatActivity {

    TextView total, totalRequests;

    // Donor stats
    TextView aplus, aminus, bplus, bminus, oplus, ominus, abplus, abminus;

    // Request stats
    TextView reqAplus, reqAminus, reqBplus, reqBminus, reqOplus, reqOminus, reqABplus, reqABminus;

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

        db = new DatabaseHelper(this);

        // ✅ Top stats
        total = findViewById(R.id.tvTotal);
        totalRequests = findViewById(R.id.tvTotalRequests);

        total.setText("Total Donors: " + db.getTotalDonors());
        totalRequests.setText("Total Requests: " + db.getTotalRequests());

        // ✅ Donor distribution
        aplus = findViewById(R.id.tvAplus);
        aminus = findViewById(R.id.tvAminus);
        bplus = findViewById(R.id.tvBplus);
        bminus = findViewById(R.id.tvBminus);
        oplus = findViewById(R.id.tvOplus);
        ominus = findViewById(R.id.tvOminus);
        abplus = findViewById(R.id.tvABplus);
        abminus = findViewById(R.id.tvABminus);

        aplus.setText("A+ Donors: " + db.getBloodGroupCount("A+"));
        aminus.setText("A- Donors: " + db.getBloodGroupCount("A-"));

        bplus.setText("B+ Donors: " + db.getBloodGroupCount("B+"));
        bminus.setText("B- Donors: " + db.getBloodGroupCount("B-"));

        oplus.setText("O+ Donors: " + db.getBloodGroupCount("O+"));
        ominus.setText("O- Donors: " + db.getBloodGroupCount("O-"));

        abplus.setText("AB+ Donors: " + db.getBloodGroupCount("AB+"));
        abminus.setText("AB- Donors: " + db.getBloodGroupCount("AB-"));

        // ✅ Request distribution
        reqAplus = findViewById(R.id.tvReqAplus);
        reqAminus = findViewById(R.id.tvReqAminus);
        reqBplus = findViewById(R.id.tvReqBplus);
        reqBminus = findViewById(R.id.tvReqBminus);
        reqOplus = findViewById(R.id.tvReqOplus);
        reqOminus = findViewById(R.id.tvReqOminus);
        reqABplus = findViewById(R.id.tvReqABplus);
        reqABminus = findViewById(R.id.tvReqABminus);

        reqAplus.setText("A+ Requests: " + db.getRequestBloodGroupCount("A+"));
        reqAminus.setText("A- Requests: " + db.getRequestBloodGroupCount("A-"));

        reqBplus.setText("B+ Requests: " + db.getRequestBloodGroupCount("B+"));
        reqBminus.setText("B- Requests: " + db.getRequestBloodGroupCount("B-"));

        reqOplus.setText("O+ Requests: " + db.getRequestBloodGroupCount("O+"));
        reqOminus.setText("O- Requests: " + db.getRequestBloodGroupCount("O-"));

        reqABplus.setText("AB+ Requests: " + db.getRequestBloodGroupCount("AB+"));
        reqABminus.setText("AB- Requests: " + db.getRequestBloodGroupCount("AB-"));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}