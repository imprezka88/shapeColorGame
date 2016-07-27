package com.ewareza.shapegame.app;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DisplayThread extends Thread {
    private static final Logger log = Logger.getLogger(DisplayThread.class.getName());
    private final SurfaceHolder surfaceHolder;
    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicBoolean finish = new AtomicBoolean(false);

    public DisplayThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        while (running.get()) {
            updatePhysics();

            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();

                if (canvas != null) {
                    synchronized (surfaceHolder) {
                        clearScreen(canvas);

                        if(!isFinish())
                            drawUpdatedView(canvas);
                        else
                            running.set(false);
                    }
                }
            } finally {
                tryToUnlockCanvas(canvas);
            }

            tryToSleep();
        }
    }

    private void tryToUnlockCanvas(Canvas canvas) {
        if (canvas != null) {
            try {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            catch (Exception e) {
                log.log(Level.WARNING, e.getMessage(), e);
            }
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

    public void setFinish(boolean clearFinish) {
        synchronized (surfaceHolder) {
            finish.set(clearFinish);
        }
    }

    private boolean isFinish() {
        return finish.get();
    }
}
