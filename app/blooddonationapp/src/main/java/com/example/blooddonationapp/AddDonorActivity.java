package com.example.blooddonationapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Database.DatabaseHelper;
import com.example.blooddonationapp.Model.Person;

public class AddDonorActivity extends AppCompatActivity {

    EditText etName,etPhone,etLocation;
    Button btnSave;
    DatabaseHelper db;
    Spinner spBloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Donor");
        }

        spBloodGroup=findViewById(R.id.spBloodGroup);

        String[] bloodGroups={"Select Blood Group","A+","A-","B+","B-","O+","O-","AB+","AB-"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bloodGroups);
        spBloodGroup.setAdapter(adapter);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etLocation = findViewById(R.id.etLocation);
        btnSave = findViewById(R.id.btnSave);

        db = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String blood = spBloodGroup.getSelectedItem().toString();
            String phone = etPhone.getText().toString().trim();
            String Location = etLocation.getText().toString().trim();

            if(name.isEmpty() || blood.isEmpty() || phone.isEmpty() || Location.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(phone.length()!=10){
                etPhone.setError("Phone number must be exactly 10 digits");
                etPhone.requestFocus();
                return;
            }
            if(blood.equals("Select Blood Group")){
                Toast.makeText(this,"Please select blood group",Toast.LENGTH_SHORT).show();
                return;
            }

            Person person = new Person(0,name, blood, phone, Location);
            db.addDonor(person);

            Toast.makeText(this, "Donor Added Successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}