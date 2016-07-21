package com.ewareza.shapegame.app.shapeColorGame;

import android.graphics.Canvas;
import com.ewareza.shapegame.app.Game;
import com.ewareza.shapegame.app.GameEngine;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ShapeColorGame extends Game {
    private static GameEngine engine;
    private static List<Integer> gameSpeeds = Arrays.asList(500, 300, 100, 50, 0);
    private static List<Integer> moveSteps = Arrays.asList(0, 0, 1, 1, 1, 2, 3, 4, 5, 10, 15);
    private static AtomicInteger gameNumber = new AtomicInteger(0);
    private static AtomicInteger currentMoveStep = new AtomicInteger(0);
    private static AtomicInteger currentGameSpeed = new AtomicInteger(0);
    private static AtomicBoolean gameOver = new AtomicBoolean(false);

    public ShapeColorGame(String gameType) {
        engine = new GameEngine(gameType);
    }

    public static void setToFirstGame() {
        gameNumber.set(0);
    }

    public static synchronized void incrementGameNumber() {
        gameNumber.incrementAndGet();
        currentMoveStep.set(moveSteps.get(Math.min(gameNumber.get(), moveSteps.size() - 1)));
        currentGameSpeed.set(gameSpeeds.get(Math.min(gameNumber.get(), gameSpeeds.size() - 1)));
    }

    public static int getGameNumber() {
        return gameNumber.get();
    }

    public static GameEngine getEngine() {
        return engine;
    }

    public static long getGameSpeedForCurrentGame() {
        return currentGameSpeed.get();
    }

    public static int getStepForCurrentGame() {
        return currentMoveStep.get();
    }

    public static void updatePhysics() {
        engine.update();
    }

    public static int getNumberOfDifferentGameSpeeds() {
        return moveSteps.size();
    }

    public static void drawGameTitleShape(Canvas canvas) {
        engine.drawGameTitleShape(canvas);
    }

    public static boolean isGameOver() {
        return gameOver.get();
    }

    public static void setGameOver(boolean gameOver) {
        ShapeColorGame.gameOver.set(gameOver);
    }

    @Override
    public void setToInitialState() {
        ShapeColorGame.setToFirstGame();
    }
}
