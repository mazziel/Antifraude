package com.pe.af.android.data.repository.datasource.generic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.modelmapper.TypeToken;

import java.util.HashMap;

public class PreferenceStore<T> {

    private final Context context;

    public PreferenceStore(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }


    public void saveOnSharePreferences(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveOnSharePreferencesObject(String key, T request) {
        Gson gson = new Gson();
        String hashMapString = gson.toJson(request);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, hashMapString);
        editor.commit();
    }

    public void saveOnSharePreferencesHashMap(String key, HashMap<String, String> hashMap) {
        Gson gson = new Gson();
        String hashMapString = gson.toJson(hashMap);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, hashMapString);
        editor.commit();
    }

    public void saveOnSharePreferences(String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void saveOnSharePreferences(String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void saveOnSharePreferences(String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getPreference(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public String getPreferenceObject(String key) {
        //convert to string using gson
        Gson gson = new Gson();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String stored = preferences.getString(key, "");
        return stored;
    }

    public HashMap<String, Object> getPreferenceHashMap(String key) {
        //convert to string using gson
        Gson gson = new Gson();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String storedHashMapString = preferences.getString(key, "");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        return gson.fromJson(storedHashMapString, type);
    }

    public long getPreferenceLong(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(key, 0);
    }

    public int getPreferenceInt(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }

    public boolean getPreferenceBoolean(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }

    public void removePreferenceObject(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
