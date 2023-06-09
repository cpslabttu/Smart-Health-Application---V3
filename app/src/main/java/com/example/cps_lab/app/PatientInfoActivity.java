package com.example.cps_lab.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cps_lab.R;

public class PatientInfoActivity extends AppCompatActivity {

    EditText editTextZipCode, editTextAge;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale, radioButtonPreferNotToSay;
    Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info2);

        // Initialize views
        editTextZipCode = findViewById(R.id.editTextZipCode);
        editTextAge = findViewById(R.id.editTextAge);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonPreferNotToSay = findViewById(R.id.radioButtonPreferNotToSay);
        buttonStart = findViewById(R.id.buttonStart);

        // Handle button click
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected gender
                String gender = "";
                int selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
                if (selectedRadioButtonId == R.id.radioButtonMale) {
                    gender = "Male";
                } else if (selectedRadioButtonId == R.id.radioButtonFemale) {
                    gender = "Female";
                } else if (selectedRadioButtonId == R.id.radioButtonPreferNotToSay) {
                    gender = "Prefer not to say";
                }

                // Get the entered zip code and age
                String zipCode = editTextZipCode.getText().toString();
                String age = editTextAge.getText().toString();

                String patientInfoAll = "Zip Code: " + zipCode + "\n"
                        + "Age: " + age + "\n"
                        + "Gender: " + gender;
                // Save the information in shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences("Patient", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("patientInfo", patientInfoAll);
                editor.apply();

                Intent intent = new Intent(PatientInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
