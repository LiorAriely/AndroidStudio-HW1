package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1.Fragments.ListFragment;
import com.example.hw1.Fragments.MapsFragment;
import com.example.hw1.Interface.Callback_List;
import com.google.android.material.button.MaterialButton;

public class ScoreActivity extends AppCompatActivity {
    private MaterialButton score_BTN_menu;
    private MapsFragment mapFragment;
    private ListFragment listFragment;

    private Callback_List callback_list = new Callback_List() {
        @Override
        public void setMapLocation(double lat, double lon, String name) {
            mapFragment.setMapLocation(lat,lon,name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        listFragment = new ListFragment();
        mapFragment = new MapsFragment();
        listFragment.setCallBack_list(callback_list);
        findViews();
        getSupportFragmentManager().beginTransaction().add(R.id.score_list,listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.score_map,mapFragment).commit();

        score_BTN_menu.setOnClickListener(view -> {
            returnToMenu();
        });

    }

    private void findViews(){
        this.score_BTN_menu = findViewById(R.id.score_BTN_Menu);
    }

    private void returnToMenu() {
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
        finish();
    }

}
