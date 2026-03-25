package com.example.blooddonationapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import androidx.appcompat.widget.SwitchCompat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat darkModeSwitch;
    Spinner languageSpinner;
    Button logout;

    public static final String PREFS_NAME = "AppSettings";
    SharedPreferences sharedPreferences;

    String[] languages = {"English","Hindi","Marathi","Gujarati"};
    String[] codes = {"en","hi","mr","gu"};


    @Override
    protected void attachBaseContext(Context base) {

        SharedPreferences prefs = base.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String lang = prefs.getString("language", "en");

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration(base.getResources().getConfiguration());
        config.setLocale(locale);

        super.attachBaseContext(base.createConfigurationContext(config));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }

        darkModeSwitch = findViewById(R.id.settings_switch_mode);
        languageSpinner = findViewById(R.id.settings_spinner_language);
        logout=findViewById(R.id.btnLogout);

        logout.setOnClickListener(v -> {

            SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();
            editor.apply();

            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

        });

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);

        languageSpinner.setAdapter(adapter);

        // Load saved language
        String savedLang = sharedPreferences.getString("language","en");
        int position = getPositionOfLanguage(savedLang);

        if(position != -1){
            languageSpinner.setSelection(position);
        }

        boolean darkMode = sharedPreferences.getBoolean("darkMode", false);
        darkModeSwitch.setChecked(darkMode);

        AppCompatDelegate.setDefaultNightMode(
                darkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("darkMode", isChecked);
            editor.apply();

            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES
                            : AppCompatDelegate.MODE_NIGHT_NO
            );
        });
    }
    private int getPositionOfLanguage(String selectedCode) {

        for(int i = 0; i < codes.length; i++){
            if(codes[i].equals(selectedCode)){
                return i;
            }
        }
        return -1;
    }

    // Save language button click
    public void submitData(View view){

        int position = languageSpinner.getSelectedItemPosition();
        String code = codes[position];

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", code);
        editor.apply();

        setLocale(code);
    }

    private void setLocale(String code){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", code);
        editor.apply();

        Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}