package com.example.hw1;

import android.app.Application;

import com.example.hw1.Utilities.AppSP;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppSP.init(this);
    }
}
