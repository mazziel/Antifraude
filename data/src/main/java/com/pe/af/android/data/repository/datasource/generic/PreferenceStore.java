package com.pe.af.android.data.repository.datasource.generic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceStore {

    private final Context context;

    public PreferenceStore(Context context) {
        this.context = context;
    }
    public Context getContext(){
        return context;
    }


    public void saveOnSharePreferences(String key, String value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public void saveOnSharePreferences(String key, long value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key,value);
        editor.commit();
    }
    public void saveOnSharePreferences(String key, int value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public void saveOnSharePreferences(String key, boolean value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public String getPreference(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,"");
    }
    public long getPreferenceLong(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(key,0);
    }
    public int getPreferenceInt(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key,0);
    }

    public boolean getPreferenceBoolean(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key,false);
    }

}
