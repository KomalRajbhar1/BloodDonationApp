package com.example.blooddonationapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Database.DatabaseHelper;

public class RequestBloodActivity extends AppCompatActivity {

    EditText etPatientName, etHospital, etLocation, etPhone;
    Spinner spBloodGroup;
    Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Request Blood");
        }

        etPatientName = findViewById(R.id.etPatientName);
        spBloodGroup = findViewById(R.id.spBloodGroup);
        etHospital = findViewById(R.id.etHospital);
        etLocation = findViewById(R.id.etLocation);
        etPhone = findViewById(R.id.etPhone);
        btnRequest = findViewById(R.id.btnRequest);

        DatabaseHelper db = new DatabaseHelper(this);

        String[] bloodGroups = {
                "Select Blood Group",
                "A+", "A-", "B+", "B-",
                "O+", "O-", "AB+", "AB-"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                bloodGroups
        );

        spBloodGroup.setAdapter(adapter);

        btnRequest.setOnClickListener(v -> {

            String name = etPatientName.getText().toString().trim();
            String blood = spBloodGroup.getSelectedItem().toString();
            String hospital = etHospital.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if(name.isEmpty() || blood.equals("Select Blood Group") || phone.isEmpty()){
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(phone.length() != 10){
                Toast.makeText(this, "Enter valid 10-digit phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.addRequest(name, blood, phone, hospital, location);

            if(inserted){
                Toast.makeText(this, "Request Saved", Toast.LENGTH_SHORT).show();

                etPatientName.setText("");
                spBloodGroup.setSelection(0);
                etHospital.setText("");
                etLocation.setText("");
                etPhone.setText("");
            } else {
                Toast.makeText(this, "Error saving request", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}