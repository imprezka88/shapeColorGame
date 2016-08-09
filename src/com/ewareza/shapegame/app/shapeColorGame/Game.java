package com.ewareza.shapegame.app.shapeColorGame;

import com.ewareza.shapegame.drawer.AndroidCanvasDrawer;
import com.ewareza.shapegame.drawer.Drawer;
import com.ewareza.shapegame.mover.Mover;
import com.ewareza.shapegame.mover.NotIntersectingMover;

public abstract class Game {
    private static final AndroidCanvasDrawer drawer = new AndroidCanvasDrawer();
    private static final Mover mover = new NotIntersectingMover();

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
