package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.example.hw1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MenuActivity extends AppCompatActivity{


    // view
    private MaterialButton menu_BTN_play;
    private MaterialButton menu_BTN_top10;
    private SwitchMaterial  menu_SW_sensors;
    private SwitchMaterial  menu_SW_fast;



    // mode
    private boolean isFast=false;
    private boolean isSensors = false;

    private AppCompatEditText menu_TXT_userName;
    private double latitude = 0.0;
    private double longitude= 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        buttonListener();
        switchListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void findViews(){
        menu_BTN_play = findViewById(R.id.menu_BTN_play);
        menu_BTN_top10 = findViewById(R.id.menu_BTN_top10);
        menu_SW_sensors = findViewById(R.id.menu_SW_sensors);
        menu_SW_fast = findViewById(R.id.menu_SW_fast);
    }

    private void buttonListener(){
        menu_BTN_play.setOnClickListener(view -> {
            startGame();
        });

    }

    private void switchListener(){
        menu_SW_fast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean fast) {
                isFast = fast;
            }
        });
        menu_SW_sensors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean sensors) {
                isSensors = sensors;
            }
        });
    }

    private void startGame() {
        Intent gameIntent = new Intent(this, MainActivity.class);
        gameIntent.putExtra(MainActivity.KEY_SENSOR, isSensors);
        gameIntent.putExtra(MainActivity.KEY_SPEED, isFast);
        //gameIntent.putExtra(MainActivity.KEY_LATITUDE,latitude);
        //gameIntent.putExtra(MainActivity.KEY_LONGITUDE,longitude);
        startActivity(gameIntent);
        finish();
    }

}

