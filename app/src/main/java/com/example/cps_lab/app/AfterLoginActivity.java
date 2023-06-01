package com.example.cps_lab.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.cps_lab.R;
import com.example.cps_lab.ble.central.BlePeripheral;
import com.example.cps_lab.ble.central.BleScanner;

import java.util.List;

public class AfterLoginActivity extends AppCompatActivity {

    Button deviceConnect, diagonistic, logout, instructions;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        deviceConnect = (Button) findViewById(R.id.device_connect);
        //diagonistic = (Button) findViewById(R.id.diagonistic);
        instructions = (Button) findViewById(R.id.instructions);
        logout = (Button) findViewById(R.id.btn_logout);

        prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        editor = prefs.edit();

        deviceConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterLoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        diagonistic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AfterLoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterLoginActivity.this, InstructionsActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                List<BlePeripheral> connectedPeriperals = BleScanner.getInstance().getConnectedOrConnectingPeripherals();
                for (BlePeripheral blePeripheral : connectedPeriperals) {
                    blePeripheral.disconnect();
                }
                Intent intent = new Intent(AfterLoginActivity.this, BeforeMainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                Intent intent = new Intent(AfterLoginActivity.this, BeforeMainActivity.class);
                startActivity(intent);
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}