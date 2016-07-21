package com.ewareza.shapegame.app.shapeColorGame;

import android.graphics.Point;
import com.ewareza.shapegame.app.GameEngine;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameThread extends Thread {
    private CountDownLatch gameStartCountDownLatch;
    private CyclicBarrier gameOverCyclicBarrier;
    private GameEngine gameEngine;
    private AtomicBoolean gameStarting = new AtomicBoolean(false);
    private AtomicBoolean screenTouched = new AtomicBoolean(false);
    private Point currentTouchedPoint;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    public GameThread(CountDownLatch gameStartCountDownLatch, CyclicBarrier gameOverCyclicBarrier) {
        this.gameStartCountDownLatch = gameStartCountDownLatch;
        this.gameOverCyclicBarrier = gameOverCyclicBarrier;
        gameEngine = ShapeColorGame.getEngine();
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning.set(isRunning);
    }

    @Override
    public void run() {
        while(isRunning.get()) {
            if(gameStarting.get()) {

                gameStartCountDownLatch.countDown();
            }
        }
    }


    public synchronized void onScreenTouched(Point point) {
        screenTouched.set(true);
        currentTouchedPoint = point;
    }

    public void onStart() {
        gameStarting.set(true);
    }
}
