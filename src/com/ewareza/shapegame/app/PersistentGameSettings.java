package com.ewareza.shapegame.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.atomic.AtomicBoolean;

public class PersistentGameSettings {
    private static final String PREFS_NAME = "shapeGameSettings";
    private static final String SOUNDS_ENABLED = "soundsEnabled";
    private static final String SPEECH_ENABLED = "speechEnabled";

    private static AtomicBoolean soundsEnabled = new AtomicBoolean(true);
    private static AtomicBoolean speechEnabled = new AtomicBoolean(true);

    static void load(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        soundsEnabled.set(settings.getBoolean(SOUNDS_ENABLED, true));
        speechEnabled.set(settings.getBoolean(SPEECH_ENABLED, true));
    }

    static void save(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(SOUNDS_ENABLED, soundsEnabled.get());
        editor.putBoolean(SPEECH_ENABLED, speechEnabled.get());

        editor.apply();
    }

    public static boolean getSoundsEnabled() {
        return soundsEnabled.get();
    }

    public static boolean getSpeechEnabled() {
        return speechEnabled.get();
    }

    public static void setSpeechEnabled(boolean speechEnabled) {
        PersistentGameSettings.speechEnabled.set(speechEnabled);
    }

    public static void setSoundsEnabled(boolean soundsEnabled) {
        PersistentGameSettings.soundsEnabled.set(soundsEnabled);
    }
}
