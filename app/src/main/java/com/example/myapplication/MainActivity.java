package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText _main_et_username,_main_et_password;
    TextView _main_tv_forgotPassword,_main_tv_newuser;
    Button _main_btn_submit;
    String username,password;
    public static final String myPreference = "myPrefs";
    SharedPreferences sharedPreferences;

    @Override
    protected void attachBaseContext(Context base){
        SharedPreferences prefs=base.getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        String lang=prefs.getString("language","en");
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);

        Configuration config=new Configuration(base.getResources().getConfiguration());
        config.setLocale(locale);
        super.attachBaseContext(base.createConfigurationContext(config));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences=getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        boolean isDarkMode=sharedPreferences.getBoolean("darkMode",false);
        if(isDarkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Toast.makeText(this, "onCreate() called", Toast.LENGTH_SHORT).show();
        _main_et_username = findViewById(R.id.main_et_username);
        _main_et_password = findViewById(R.id.main_et_password);
        _main_tv_forgotPassword=findViewById(R.id.main_tv_ForgotPassword);
        _main_tv_newuser=findViewById(R.id.main_tv_newUser);
        _main_btn_submit = findViewById(R.id.main_btn_login);

        if(sharedPreferences.getAll().containsKey("isLogin")){
            if(sharedPreferences.getString("isLogin","").equals("1")){
                if(sharedPreferences.getAll().containsKey("username")){
                    if(sharedPreferences.getString("username","")!=""){
                        Intent i = new Intent(MainActivity.this,Home.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        }

        _main_tv_newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Registration.class);
                startActivity(i);
                finish();
            }
        });
        _main_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = _main_et_username.getText().toString().trim();
                password = _main_et_password.getText().toString().trim();
                if(username.isEmpty() && username == "" && username.length() <= 0){
                    _main_et_username.setError("Username cannot be empty");
                    _main_et_username.requestFocus();
                }
                else if(password.isEmpty() && password == "" && password.length() <= 0){
                    _main_et_password.setError("Password cannot be empty");
                    _main_et_password.requestFocus();
                }
                else if(password.length() < 8){
                    _main_et_password.setError("Password cannot be less than 8 digits");
                    _main_et_password.requestFocus();
                }
                else{
                    if(username.equals("admin") && password.equals("123456789")){
                        Toast.makeText(MainActivity.this, "Login Validated Successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("isLogin","1");
                        editor.putString("username",username);
                        editor.commit();
                        Intent i = new Intent(MainActivity.this,Home.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        //Toast.makeText(MainActivity.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Login Failed");
                        builder.setMessage("Invalid Username or Password");
                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.setNegativeButton("Ok",null);
                        builder.setNeutralButton("Done",null);
                        builder.setCancelable(false);
                        builder.show();
                    }
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart() called", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume() called", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop() called", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause() called", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart() called", Toast.LENGTH_SHORT).show();
    }
    private void setLocale(String code){
        Locale locale=new Locale(code);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}