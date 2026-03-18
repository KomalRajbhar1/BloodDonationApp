package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Aboutus extends AppCompatActivity {
    WebView webView;
    private static String url="https://www.burhanicollege.edu.in/";
    public static String myPrefs="myPrefs";
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        boolean isDarkMode = preferences.getBoolean("darkMode", false);
        if(isDarkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aboutus);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        webView=findViewById(R.id.aboutus_webView);
        loadURL(url);
    }
    public void loadURL(String url){
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollbarFadingEnabled(false);
        webView.loadUrl(url);
    }
}