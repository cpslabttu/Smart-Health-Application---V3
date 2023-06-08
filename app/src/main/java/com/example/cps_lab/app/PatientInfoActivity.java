package com.example.cps_lab.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cps_lab.R;

public class PatientInfoActivity extends AppCompatActivity {

    EditText zipcode, age, gender;
    Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info2);

        zipcode = (EditText)findViewById(R.id.zipcode);
        age = (EditText)findViewById(R.id.age);
        gender = (EditText)findViewById(R.id.gender);

        btn_start = (Button)findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredZipCode = zipcode.getText().toString();
                String enteredAge = age.getText().toString();
                String enteredGender = gender.getText().toString();

                // Save the information in shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("zipcode", enteredZipCode);
                editor.putString("age", enteredAge);
                editor.putString("gender", enteredGender);
                editor.apply();

                // Start the other fragment or activity
                // ...
            }
        });

    }
}