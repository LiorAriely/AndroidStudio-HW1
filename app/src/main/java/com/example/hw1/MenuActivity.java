package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
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
    private boolean isFast;
    private boolean isSensorMode;

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        buttonListener();
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
            openGameScreen();
        });

    }

    private void openGameScreen() {
        Intent gameIntent = new Intent(this, MainActivity.class);
        //gameIntent.putExtra(MainActivity.KEY_SENSOR, isSensorMode);
        //gameIntent.putExtra(MainActivity.KEY_SPEED, isFast);
        //gameIntent.putExtra(MainActivity.KEY_LATITUDE,latitude);
        //gameIntent.putExtra(MainActivity.KEY_LONGITUDE,longitude);
        startActivity(gameIntent);
        finish();
    }

}

