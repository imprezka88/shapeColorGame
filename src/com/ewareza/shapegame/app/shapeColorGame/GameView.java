package com.ewareza.shapegame.app.shapeColorGame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.ewareza.shapegame.app.utils.GameUtils;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final static Logger logger = Logger.getLogger(GameView.class.getName());
    private CountDownLatch gameStartCountDownLatch;
    private ShapeGameDisplayThread displayThread;
    private SurfaceHolder holder;

    public GameView(Context context, CountDownLatch gameStartCountDownLatch) {
        super(context);
        this.gameStartCountDownLatch = gameStartCountDownLatch;
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);

        displayThread = new ShapeGameDisplayThread(holder);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!displayThread.isRunning()) {
            //@TODO check if needed
            displayThread = new ShapeGameDisplayThread(holder);
            tryToAwaitOnLatch();
            displayThread.setRunning(true);
            displayThread.start();
        } else {
            tryToAwaitOnLatch();
            displayThread.setRunning(true);
            displayThread.start();
        }
    }

    private void tryToAwaitOnLatch() {
        try {
            gameStartCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDisplayThread();
    }

    public void stopDisplayThread() {
        displayThread.setRunning(false);
        GameUtils.StopThread(displayThread);
    }
}
