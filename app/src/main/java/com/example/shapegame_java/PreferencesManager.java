package com.example.shapegame_java;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String VOLUME_KEY = "volume";
    private final SharedPreferences sharedPreferences;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences("game_preferences", Context.MODE_PRIVATE);
    }

    public void saveVolume(float volume) {
        sharedPreferences.edit().putFloat(VOLUME_KEY, volume).apply();
    }

    public float getVolume() {
        return sharedPreferences.getFloat(VOLUME_KEY, 0.5f);
    }
}
