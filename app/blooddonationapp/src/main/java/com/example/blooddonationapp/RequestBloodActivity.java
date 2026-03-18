package com.example.blooddonationapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blooddonationapp.Database.DatabaseHelper;

public class RequestBloodActivity extends AppCompatActivity {
    EditText etPatientName, etBloodGroup, etHospital, etLocation, etPhone;
    Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Request Blood");
        }

        etPatientName=findViewById(R.id.etPatientName);
        etBloodGroup=findViewById(R.id.etBloodGroup);
        etHospital=findViewById(R.id.etHospital);
        etLocation=findViewById(R.id.etLocation);
        etPhone=findViewById(R.id.etPhone);
        btnRequest=findViewById(R.id.btnRequest);

        DatabaseHelper db = new DatabaseHelper(this);

        btnRequest.setOnClickListener(v -> {

            String name = etPatientName.getText().toString().trim();
            String blood = etBloodGroup.getText().toString().trim();
            String hospital = etHospital.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if(name.isEmpty() || blood.isEmpty() || phone.isEmpty()){
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.addRequest(name, blood, phone, hospital, location);

            if(inserted){
                Toast.makeText(this, "Request Saved", Toast.LENGTH_SHORT).show();

                // clear fields
                etPatientName.setText("");
                etBloodGroup.setText("");
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