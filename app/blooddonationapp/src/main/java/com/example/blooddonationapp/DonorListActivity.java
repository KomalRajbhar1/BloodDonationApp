package com.example.blooddonationapp;

import android.os.Bundle;
import android.view.View;
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

    String[] bloodGroups = {"Search Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Donor List");
        }

        recyclerView = findViewById(R.id.recyclerView);
        spSearchBlood = findViewById(R.id.spSearchBlood);

        db = new DatabaseHelper(this);
        List<Person> donorList = db.getAllDonors();

        adapter = new PersonAdapter(this, donorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // ✅ Spinner setup
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                bloodGroups
        );

        spSearchBlood.setAdapter(spinnerAdapter);

        // ✅ Spinner filter logic
        spSearchBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getItemAtPosition(position).toString();

                if (position == 0) {
                    adapter.updateList(db.getAllDonors());
                } else {
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

        List<Person> donorList = db.getAllDonors();
        adapter.updateList(donorList);
    }

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}