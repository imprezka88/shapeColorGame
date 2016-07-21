package com.ewareza.shapegame.app;

import android.app.Application;
import com.ewareza.shapegame.resources.DimenRes;
import com.ewareza.shapegame.resources.ImageResources;
import com.ewareza.shapegame.resources.SoundResources;
import com.ewareza.shapegame.resources.TextResources;

public class ShapeGameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initResources();
    }

    private void initResources() {
        SoundResources.getInstance().init(this);
        TextResources.getInstance().init(this);
        DimenRes.getInstance().init(this);
        ImageResources.getInstance().init(this);
    }
}
