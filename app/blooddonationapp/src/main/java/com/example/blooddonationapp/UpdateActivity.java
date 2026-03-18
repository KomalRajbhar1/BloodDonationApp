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

public class UpdateActivity extends AppCompatActivity {

    EditText etName, etPhone, etArea;
    Spinner spBlood;
    Button btnUpdate;
    DatabaseHelper db;
    int id;

    String[] bloodGroups = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Update Donor");
        }

        etName = findViewById(R.id.etUpdateName);
        spBlood = findViewById(R.id.spBlood);
        etPhone = findViewById(R.id.etUpdatePhone);
        etArea = findViewById(R.id.etUpdateArea);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = new DatabaseHelper(this);

        // ✅ Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                bloodGroups
        );
        spBlood.setAdapter(adapter);

        // ✅ Get data from intent
        id = getIntent().getIntExtra("id", -1);
        String oldName = getIntent().getStringExtra("name");
        String oldBlood = getIntent().getStringExtra("blood");
        String oldPhone = getIntent().getStringExtra("phone");
        String oldArea = getIntent().getStringExtra("area");

        etName.setText(oldName);
        etPhone.setText(oldPhone);
        etArea.setText(oldArea);

        // ✅ Set old blood group in spinner
        for (int i = 0; i < bloodGroups.length; i++) {
            if (bloodGroups[i].equals(oldBlood)) {
                spBlood.setSelection(i);
                break;
            }
        }

        btnUpdate.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String blood = spBlood.getSelectedItem().toString(); // ✅ FIXED
            String phone = etPhone.getText().toString().trim();
            String area = etArea.getText().toString().trim();

            // ✅ Validation
            if(name.isEmpty()){
                etName.setError("Enter name");
                return;
            }

            if(phone.length() != 10){
                etPhone.setError("Enter valid 10-digit number");
                return;
            }

            if(area.isEmpty()){
                etArea.setError("Enter area");
                return;
            }

            Person person = new Person(id, name, blood, phone, area);
            int result = db.updateDonor(person);

            if(result > 0){
                Toast.makeText(this,"Donor Updated", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // ✅ important for refresh
                finish();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}