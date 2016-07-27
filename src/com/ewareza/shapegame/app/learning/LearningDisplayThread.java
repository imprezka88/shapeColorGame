package com.ewareza.shapegame.app.learning;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import com.ewareza.shapegame.app.DisplayThread;

class LearningDisplayThread extends DisplayThread {
    private static final int TIME_BETWEEN_FRAMES = 0;
    private LearningScreen learningScreen;

    LearningDisplayThread(SurfaceHolder holder) {
        super(holder);
    }

    @Override
    protected void updatePhysics() {
        learningScreen = LearningGame.getInstance().getLearningScreen();
        if (LearningGame.getInstance().getShouldUpdateScreen())
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
