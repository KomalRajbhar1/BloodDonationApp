package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.Arrays;
import java.util.Locale;

public class settings extends AppCompatActivity {
    Switch aSwitch;
    public static final String myPrefs="myPrefs";
    SharedPreferences sharedPreferences;
    Spinner _settings_spinner_language;

    String[] languages={"English","Hindi","Marathi","Gujrati"};
    String[] codes={"en","hi","mr","gu"};
    protected void attachBaseContext(Context base) {
        // Load saved language and apply locale before super.attachBaseContext
        SharedPreferences prefs = base.getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        String lang = prefs.getString("language", "en");  // Default to "en" if not set
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration(base.getResources().getConfiguration());
        config.setLocale(locale);
        super.attachBaseContext(base.createConfigurationContext(config));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            aSwitch = findViewById(R.id.setting_switch_mode);
            sharedPreferences = getSharedPreferences(myPrefs, MODE_PRIVATE);
            _settings_spinner_language = findViewById(R.id.settings_spinner_language);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
            _settings_spinner_language.setAdapter(adapter);
            if(sharedPreferences.getAll().containsKey("language")){
                if(sharedPreferences.getString("language", "") != ""){
                    setLocale(sharedPreferences.getString("language", ""),"onCreate");
                    int position = getPositionOfLanguage(sharedPreferences.getString("language", ""));
                    Toast.makeText(this, "Position: " + position, Toast.LENGTH_SHORT).show();
                    if(position != -1){
                        _settings_spinner_language.setSelection(position);
                    }
                }
                else{
                    setLocale("en","onCreate");
                }
            }
            if(sharedPreferences.getAll().containsKey("darkMode")){
                if(sharedPreferences.getBoolean("darkMode", false)){
                    aSwitch.setChecked(true);
                }
                else{
                    aSwitch.setChecked(false);
                }
            }
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("darkMode");
                        editor.putBoolean("darkMode", isChecked);
                        editor.commit();
                        editor.apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    else{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("darkMode");
                        editor.putBoolean("darkMode", isChecked);
                        editor.commit();
                        editor.apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
            });
        }
        private int getPositionOfLanguage(String selectedCode) {
            int position = -1;
            if (selectedCode != null) {
                for (int i = 0; i < codes.length; i++) {
                    if (codes[i].equals(selectedCode)) {
                        position = i;
                        break;
                    }
                }
            }
            return position;
        }
        public void submitData(View view){
            int position = _settings_spinner_language.getSelectedItemPosition();
            String code = codes[position];
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("language", code);
            editor.commit();
            editor.apply();
            setLocale(code,"onSubmit");
        }

        private void setLocale(String code,String old_new) {
            Locale locale = new Locale(code);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            if(old_new.equals("onSubmit")){
                Intent i = new Intent(settings.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }