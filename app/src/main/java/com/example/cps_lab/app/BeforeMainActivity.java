package com.example.cps_lab.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cps_lab.R;

import java.io.IOException;

public class BeforeMainActivity extends AppCompatActivity {

    Button logIn, signUp, guestAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_main);

        logIn= (Button) findViewById(R.id.login);
        signUp= (Button) findViewById(R.id.signup);
        guestAccess= (Button) findViewById(R.id.guestaccess);



        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeforeMainActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeforeMainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        guestAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeforeMainActivity.this, PatientInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}