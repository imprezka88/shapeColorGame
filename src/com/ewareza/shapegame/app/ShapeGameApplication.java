package com.ewareza.shapegame.app;

import android.app.Application;
import com.ewareza.shapegame.resources.ScaledDimenRes;
import com.ewareza.shapegame.resources.ImageResources;
import com.ewareza.shapegame.resources.SoundResources;
import com.ewareza.shapegame.resources.TextResources;

public class ShapeGameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        loadPersistentSettings();
        initResources();
    }

    private void loadPersistentSettings() {
        PersistentGameSettings.load(this.getApplicationContext());
    }

    private void initResources() {
        SoundResources.getInstance().init(this.getApplicationContext());
        TextResources.getInstance().init(this.getApplicationContext());
        ScaledDimenRes.getInstance().init(this.getApplicationContext());
        ImageResources.getInstance().init(this.getApplicationContext());
    }
}
