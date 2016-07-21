package com.ewareza.shapegame.app.learning;

import android.graphics.Canvas;
import android.graphics.Point;

public interface LearningScreen {
    void drawShapes(Canvas canvas);

    void update();

    void setToInitialState();

    void clearScreen(Canvas canvas);
}
