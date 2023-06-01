package com.example.cps_lab.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// based on: https://www.bignerdranch.com/blog/splash-screens-the-right-way/
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, BeforeMainActivity.class);
        startActivity(intent);
        finish();
    }
}
