package com.example.hw1.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
public class AppSP {
    private static final String DB_FILE = "DB_FILE";
    private static AppSP appSP = null;
    private SharedPreferences preferences;

    public static AppSP getInstance(){
        return appSP;
    }
    public static void init(Context context){
        if(appSP == null){
            appSP= new AppSP(context);
        }

    }

    private AppSP(Context context){
        preferences = context.getSharedPreferences(DB_FILE,Context.MODE_PRIVATE);
    }

    public String getStrSP(String key,String def){
        return preferences.getString(key,def);
    }

    public void putString(String key,String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

}
