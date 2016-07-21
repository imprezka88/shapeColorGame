package com.ewareza.shapegame.app.learning;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import com.ewareza.shapegame.app.DisplayThread;

public class LearningDisplayThread extends DisplayThread {
    public static final int TIME_BETWEEN_FRAMES = 0;
    private LearningScreen learningScreen;

    public LearningDisplayThread(SurfaceHolder holder) {
        super(holder);
    }

    @Override
    protected void updatePhysics() {
        learningScreen = LearningGame.getLearningScreen();
        if (LearningGame.getShouldUpdateScreen())
            learningScreen.update();
    }

    @Override
    protected void drawUpdatedView(Canvas canvas) {
        drawShape(canvas);
    }

    private void drawShape(Canvas canvas) {
        learningScreen.drawShapes(canvas);
    }

    public void clearScreen(Canvas canvas) {
        learningScreen.clearScreen(canvas);
    }

    @Override
    protected void tryToSleep() {
        try {
            Thread.sleep(TIME_BETWEEN_FRAMES);
        } catch (InterruptedException e) {
            //@TODO
        }
    }
}
