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
import android.widget.Toast;

import com.example.hw1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import im.delight.android.location.SimpleLocation;

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
    private SimpleLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.location = new SimpleLocation(this);
        requestLocationPermission(location);
        findViews();
        buttonListener();
        switchListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requestLocationPermission(SimpleLocation location) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);

        }
        putLatLon(location);
    }

    private void putLatLon(SimpleLocation location){
        location.beginUpdates();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    private void findViews(){
        menu_TXT_userName = findViewById(R.id.menu_TXT_userName);
        menu_BTN_play = findViewById(R.id.menu_BTN_play);
        menu_BTN_top10 = findViewById(R.id.menu_BTN_top10);
        menu_SW_sensors = findViewById(R.id.menu_SW_sensors);
        menu_SW_fast = findViewById(R.id.menu_SW_fast);
    }

    private void buttonListener(){
        menu_BTN_play.setOnClickListener(view -> {
            if (menu_TXT_userName.getText().length() != 0) {
            startGame();
            } else {
                Toast.makeText(this, "Please enter your name!!!", Toast.LENGTH_SHORT).show();
            }
        });

        menu_BTN_top10.setOnClickListener(view -> {
                Intent score_intent = new Intent(this, ScoreActivity.class);
                startActivity(score_intent);
                finish();
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
        gameIntent.putExtra(MainActivity.KEY_USER, menu_TXT_userName.getText().toString());
        gameIntent.putExtra(MainActivity.KEY_LATITUDE,latitude);
        gameIntent.putExtra(MainActivity.KEY_LONGITUDE,longitude);
        startActivity(gameIntent);
        finish();
    }

}

