package com.ewareza.shapegame.app;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import com.ewareza.shapegame.resources.ScaledDimenRes;
import com.ewareza.shapegame.resources.ImageResources;
import com.ewareza.shapegame.resources.SoundResources;
import com.ewareza.shapegame.resources.TextResources;

import java.util.Locale;

public class ShapeGameApplication extends Application {
    private LanguageState languageState;

    @Override
    public void onCreate() {
        super.onCreate();
        initPersistentSettings();
        initResources();
        languageState = LanguageStateFactory.getState(PersistentGameSettings.getLocale());
    }

    private void initPersistentSettings() {
        PersistentGameSettings.init(this.getApplicationContext());
    }

    private void initResources() {
        SoundResources.getInstance().init(this.getApplicationContext());
        TextResources.getInstance().init(this.getApplicationContext());
        ScaledDimenRes.getInstance().init(this.getApplicationContext());
        ImageResources.getInstance().init(this.getApplicationContext());
    }

    public void setLanguageState(LanguageState languageState) {
        this.languageState = languageState;
        setNewLocale();
    }

    private void setNewLocale() {
        Locale locale = languageState.getLocale();
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        PersistentGameSettings.setLocale(locale);
    }

    public LanguageState getLanguageState() {
        return languageState;
    }
}
