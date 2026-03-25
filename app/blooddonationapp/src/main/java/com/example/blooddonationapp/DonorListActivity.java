package com.example.blooddonationapp;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.Adapter.PersonAdapter;
import com.example.blooddonationapp.Database.DatabaseHelper;
import com.example.blooddonationapp.Model.Person;

import java.util.List;

public class DonorListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PersonAdapter adapter;
    DatabaseHelper db;
    Spinner spSearchBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        recyclerView = findViewById(R.id.recyclerView);
        spSearchBlood = findViewById(R.id.spSearchBlood);

        db = new DatabaseHelper(this);
        List<Person> donorList = db.getAllDonors();

        adapter = new PersonAdapter(this, donorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // ✅ UPDATED Spinner Data (Added "All")
        String[] bloodGroups = {
                "Search Blood Group",
                "All",
                "A+", "A-", "B+", "B-",
                "O+", "O-", "AB+", "AB-"
        };

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                bloodGroups
        );

        spSearchBlood.setAdapter(adapterSpinner);

        // 🔍 Filter Logic
        spSearchBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {

                String selected = bloodGroups[position];

                if (position == 0 || selected.equals("All")) {
                    // Show all donors
                    adapter.updateList(db.getAllDonors());
                } else {
                    // Filter specific blood group
                    adapter.filterByBlood(selected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateList(db.getAllDonors());
    }
}