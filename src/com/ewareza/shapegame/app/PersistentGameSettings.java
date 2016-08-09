package com.ewareza.shapegame.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class PersistentGameSettings {
    private static final String DEFAULT_LOCALE = "en";
    private static PersistentGameSettings persistentGameSettings;

    private static final String PREFS_NAME = "shapeGameSettings_fake";
    private static final String SOUNDS_ENABLED = "soundsEnabled";
    private static final String SPEECH_ENABLED = "speechEnabled";
    private static final String LOCALE = "locale";

    private static SharedPreferences settings;

    private PersistentGameSettings(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public static void init(Context context) {
        if(persistentGameSettings == null) {
            persistentGameSettings = new PersistentGameSettings(context);
        }
    }

    static void saveBool(String name, boolean value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(name, value);

        editor.commit();
    }

    public static boolean getSoundsEnabled() {
        return settings.getBoolean(SOUNDS_ENABLED, true);
    }

    public static boolean getSpeechEnabled() {
        return settings.getBoolean(SPEECH_ENABLED, true);
    }

    public static void setSpeechEnabled(boolean speechEnabled) {
        saveBool(SPEECH_ENABLED, speechEnabled);
    }

    public static void setSoundsEnabled(boolean soundsEnabled) {
        saveBool(SOUNDS_ENABLED, soundsEnabled);
    }

    public static Locale getLocale() {
        return new Locale(settings.getString(LOCALE, DEFAULT_LOCALE));
    }

    public static void setLocale(Locale locale) {
        saveString(LOCALE, locale.getLanguage());
    }

    private static void saveString(String name, String value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(name, value);

        editor.commit();
    }
}
