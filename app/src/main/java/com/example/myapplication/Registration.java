package com.example.myapplication;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;


public class Registration extends AppCompatActivity {
    EditText _registration_et_username,_registration_et_password,_registration_et_email,_registration_et_contact;
    TextInputLayout _registration_etl_username,_registration_etl_password,_registration_etl_email,_registration_etl_contact;
    RadioGroup _registration_radioGroup_Gender;
    RadioButton _registration_rb_male,_registration_rb_female;
    Spinner _registration_spinner_status;
    CheckBox _registration_checkbox_tnc;
    Button _registration_btn_submit;
    TextView _registration_tv_data;
    String username,password,email,contact,data;
    SharedPreferences sharedPreferences;
    public static final String myPrefs= "myPrefs";

    protected void attachBaseContext(Context base){
        SharedPreferences prefs=base.getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        String lang=prefs.getString("language","en");
        Locale locale =new Locale(lang);
        Locale.setDefault(locale);

        Configuration config=new Configuration(base.getResources().getConfiguration());
        config.setLocale(locale);
        super.attachBaseContext(base.createConfigurationContext(config));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        _registration_et_username = findViewById(R.id.registration_et_username);
        _registration_et_password = findViewById(R.id.registration_et_password);
        _registration_et_email = findViewById(R.id.registration_et_email);
        _registration_et_contact = findViewById(R.id.registration_et_contact);
        _registration_radioGroup_Gender = findViewById(R.id.registration_radioGroup_Gender);
        _registration_rb_male = findViewById(R.id.registration_rb_male);
        _registration_rb_female = findViewById(R.id.registration_rb_female);
        _registration_spinner_status = findViewById(R.id.registration_spinner_status);
        _registration_checkbox_tnc = findViewById(R.id.registration_checkbox_tnc);
        _registration_btn_submit = findViewById(R.id.registration_btn_submit);
        _registration_tv_data = findViewById(R.id.registration_tv_data);
        _registration_etl_username = findViewById(R.id.registration_etl_username);
        _registration_etl_password = findViewById(R.id.registration_etl_password);
        _registration_etl_email = findViewById(R.id.registration_etl_email);
        _registration_etl_contact = findViewById(R.id.registration_etl_contact);

        sharedPreferences=getSharedPreferences(myPrefs, Context.MODE_PRIVATE);


        _registration_btn_submit.setEnabled(false);

        _registration_checkbox_tnc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean checked) {
                if(checked){
                    _registration_btn_submit.setEnabled(true);
                }
                else{
                    _registration_btn_submit.setEnabled(false);
                }
            }
        });
    }
    public void registerData(View view){
        username = _registration_et_username.getText().toString().trim();
        password = _registration_et_password.getText().toString().trim();
        email = _registration_et_email.getText().toString().trim();
        contact = _registration_et_contact.getText().toString().trim();
        int selectedId = _registration_radioGroup_Gender.getCheckedRadioButtonId();
        String status = _registration_spinner_status.getSelectedItem().toString().trim();
        boolean tnc = _registration_checkbox_tnc.isChecked();

        if(username.isEmpty()){
            _registration_etl_username.setError("Username cannot be empty");
            _registration_etl_username.requestFocus();
            return;
        }
        else{
            _registration_etl_username.setErrorEnabled(false);
        }
        if(password.isEmpty()){
            _registration_etl_password.setError("Password cannot be empty");
            _registration_etl_password.requestFocus();
            return;
        }
        else{
            _registration_etl_password.setErrorEnabled(false);
        }
        if(password.length() < 8){
            _registration_etl_password.setError("Password cannot be less than 8 digits");
            _registration_etl_password.requestFocus();
            return;
        }
        else{
            _registration_etl_password.setErrorEnabled(false);
        }
        if(email.isEmpty()){
            _registration_etl_email.setError("Email cannot be empty");
            _registration_etl_email.requestFocus();
            return;
        }
        else{
            _registration_etl_email.setErrorEnabled(false);
        }
        if(contact.isEmpty()){
            _registration_etl_contact.setError("Contact cannot be empty");
            _registration_etl_contact.requestFocus();
            return;
        }
        else{
            _registration_etl_contact.setErrorEnabled(false);
        }
        if(contact.length() != 10){
            _registration_etl_contact.setError("Contact cannot be less than 10 digits");
            _registration_etl_contact.requestFocus();
            return;
        }
        else{
            _registration_etl_contact.setErrorEnabled(false);
        }
        if(selectedId == _registration_rb_male.getId()){

        }
        else if(selectedId == _registration_rb_female.getId()){

        }
        else{
            _registration_tv_data.setText("Please select Gender");
            _registration_tv_data.setTextColor(getResources().getColor(R.color.red));
            _registration_tv_data.setTextSize(25);
            return;
        }
        if(status.isEmpty()){
            _registration_tv_data.setText("Please select Status");
            _registration_tv_data.setTextColor(getResources().getColor(R.color.red));
            _registration_tv_data.setTextSize(25);
            return;
        }
        if(! tnc){
            _registration_tv_data.setText("Please accept Terms and Conditions");
            _registration_tv_data.setTextColor(getResources().getColor(R.color.red));
            _registration_tv_data.setTextSize(25);
            return;
        }

        data = "Username: " + username + "\n";
        data += "Password: " + password + "\n";
        data += "Email: " + email + "\n";
        data += "Contact: " + contact + "\n";
        data += "Gender: " + (selectedId == _registration_rb_male.getId() ? "Male" : "Female") + "\n";
        data += "Status: " + status + "\n";
        data += "Terms and Conditions: " + (tnc ? "Accepted" : "Not Accepted") + "\n";
        _registration_tv_data.setText(data);
        _registration_tv_data.setTextColor(getResources().getColor(R.color.black));
        _registration_tv_data.setTextSize(16);
        _registration_tv_data.setVisibility(VISIBLE);
    }
    private void setLocale(String code) {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}