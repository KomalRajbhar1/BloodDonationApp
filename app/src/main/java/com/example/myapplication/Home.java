package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Person;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    SharedPreferences preferences;
    public static final String myPrefs = "myPrefs";

    Button _home_btn_logout;
    String username;

    RecyclerView _home_recyclerView;
    Button _home_btn_add;

    public int id = 0;
    List<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _home_btn_logout = findViewById(R.id.home_btn_logout);
        _home_recyclerView = findViewById(R.id.home_recyclerView);
        _home_btn_add = findViewById(R.id.home_btn_add);

        preferences = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        boolean isDarkMode = preferences.getBoolean("darkMode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        personList = new ArrayList<>();

        _home_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this);
                View dialogView = LayoutInflater.from(Home.this).inflate(R.layout.user_add_edit, null);

                dialog.setView(dialogView);
                dialog.setTitle("Add New User");

                TextInputLayout _id, _firstName, _lastName, _address;
                TextInputEditText _et_id, _et_firstName, _et_lastName, _et_address;

                _id = dialogView.findViewById(R.id.person_etl_id);
                _firstName = dialogView.findViewById(R.id.person_etl_firstName);
                _lastName = dialogView.findViewById(R.id.person_etl_lastName);
                _address = dialogView.findViewById(R.id.person_etl_address);

                _et_id = dialogView.findViewById(R.id.person_et_id);
                _et_firstName = dialogView.findViewById(R.id.person_et_firstName);
                _et_lastName = dialogView.findViewById(R.id.person_et_lastName);
                _et_address = dialogView.findViewById(R.id.person_et_address);

                _id.setVisibility(View.GONE);

                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // save logic later
                    }
                });

                dialog.setNegativeButton("Cancel", null);
                dialog.show();
            }
        });

        if (preferences.getAll().containsKey("isLogin")) {
            if (preferences.getString("isLogin", "").equals("1")) {
                if (preferences.getAll().containsKey("username")) {
                    username = preferences.getString("username", "");
                } else {
                    redirectToLogin();
                }
            } else {
                redirectToLogin();
            }
        } else {
            redirectToLogin();
        }

        _home_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("isLogin");
                editor.remove("username");
                editor.apply();

                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid = item.getItemId();
        if (itemid == R.id.home) {
            Toast.makeText(this, "home clicked", Toast.LENGTH_SHORT).show();
        } else if (itemid == R.id.aboutus) {
            Toast.makeText(this, "About us clicked", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(home.this, aboutus.class)
            startActivity(i);
        }
        else if (itemid == R.id.contactus) {
            Toast.makeText(this, "contact clicked", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void redirectToLogin() {
        Intent i = new Intent(Home.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
