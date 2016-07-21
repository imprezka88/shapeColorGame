package com.ewareza.shapegame.app;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DisplayThread extends Thread {
    protected final SurfaceHolder surfaceHolder;
    private AtomicBoolean running = new AtomicBoolean(false);

    public DisplayThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        while (running.get()) {
            updatePhysics();

            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);

                if (canvas != null) {
                    synchronized (surfaceHolder) {
                        clearScreen(canvas);
                        drawUpdatedView(canvas);
                    }
                }
            } finally {
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }

            tryToSleep();
        }
    }

    protected abstract void clearScreen(Canvas canvas);

    protected abstract void updatePhysics();

    protected abstract void drawUpdatedView(Canvas canvas);

    protected abstract void tryToSleep();

    public boolean isRunning() {
        return running.get();
    }

    public void setRunning(boolean running) {
        this.running.set(running);
    }
}
