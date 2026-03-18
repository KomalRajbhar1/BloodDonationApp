package com.example.blooddonationapp;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        recyclerView = findViewById(R.id.recyclerView);
        db = new DatabaseHelper(this);

        List<Person> requestList = db.getAllRequests();

        adapter = new PersonAdapter(this, requestList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();

        List<Person> requestList = db.getAllRequests();
        adapter.updateList(requestList);
    }
}