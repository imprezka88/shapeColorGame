package com.ewareza.shapegame.app;

import android.graphics.drawable.Drawable;
import com.ewareza.shapegame.drawer.AndroidCanvasDrawer;
import com.ewareza.shapegame.drawer.Drawer;
import com.ewareza.shapegame.mover.Mover;
import com.ewareza.shapegame.mover.NotIntersectingMover;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Game {
    private static final AndroidCanvasDrawer drawer = new AndroidCanvasDrawer();
    private static final Mover mover = new NotIntersectingMover();
    private static AtomicBoolean soundsEnabled = new AtomicBoolean(true);
    private static AtomicBoolean speechEnabled = new AtomicBoolean(true);

    public static boolean getSoundsEnabled() {
        return soundsEnabled.get();
    }

    public static void setSoundsEnabled(boolean soundsEnabled) {
        Game.soundsEnabled.set(soundsEnabled);
    }

    public static boolean getSpeechEnabled() {
        return speechEnabled.get();
    }

    public static void setSpeechEnabled(boolean getSpeakingEnabled) {
        speechEnabled.set(getSpeakingEnabled);
    }

    public static Drawer getDrawer() {
        return drawer;
    }

    public static Mover getMover() {
        return mover;
    }

    public abstract void setToInitialState();

    public abstract String getNextGameName();

    public abstract int getNextGameImageIdentifier();
}
