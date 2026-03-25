package com.example.blooddonationapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.Adapter.PersonAdapter;
import com.example.blooddonationapp.Database.DatabaseHelper;
import com.example.blooddonationapp.Model.Person;

import java.util.List;

public class RequestListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PersonAdapter adapter;
    DatabaseHelper db;
    Spinner spSearchBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        recyclerView = findViewById(R.id.recyclerView);
        spSearchBlood = findViewById(R.id.spSearchBlood);

        db = new DatabaseHelper(this);

        List<Person> requestList = db.getAllRequests();

        // 🔥 Pass TRUE (request mode)
        adapter = new PersonAdapter(this, requestList, true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // ✅ Dropdown values
        String[] bloodGroups = {
                "All",
                "A+", "A-",
                "B+", "B-",
                "O+", "O-",
                "AB+", "AB-"
        };

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                bloodGroups
        );

        spSearchBlood.setAdapter(spinnerAdapter);

        // ✅ Filter on selection
        spSearchBlood.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {

                String selected = parent.getItemAtPosition(position).toString();

                if (selected.equals("All")) {
                    adapter.updateList(db.getAllRequests());
                } else {
                    adapter.filterByBlood(selected);
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Person> requestList = db.getAllRequests();
        adapter.updateList(requestList);
    }
}