package com.example.blooddonationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    DatabaseHelper db;
    public static final String myPreference = "UserSession";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(myPreference, MODE_PRIVATE);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);

        db = new DatabaseHelper(this);

        if(sharedPreferences.getString("isLogin","").equals("1")){
            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        }

        btnLogin.setOnClickListener(view -> {

            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(username.isEmpty()){
                etUsername.setError("Username cannot be empty");
                etUsername.requestFocus();
                return;
            }

            if(password.isEmpty()){
                etPassword.setError("Password cannot be empty");
                etPassword.requestFocus();
                return;
            }

            boolean validUser = db.checkUser(username, password);

            if(validUser){

                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("isLogin","1");
                editor.putString("username",username);
                editor.apply();

                Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
            else{

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Login Failed");
                builder.setMessage("Invalid Username or Password");
                builder.setPositiveButton("OK",(d,i)->{});
                builder.show();
            }

        });

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class)));

        tvForgotPassword.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));
    }
}