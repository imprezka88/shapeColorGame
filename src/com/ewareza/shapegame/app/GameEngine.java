package com.ewareza.shapegame.app;

import android.graphics.Canvas;
import android.graphics.Point;
import com.ewareza.shapegame.app.shapeColorGame.ShapeColorGame;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleGame;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.generator.SingleGameFactory;
import com.ewareza.shapegame.player.SoundResourcesManager;

import java.util.logging.Logger;

public class GameEngine {
    private static final
    Logger logger = Logger.getLogger(GameEngine.class.getName());
    private final Object lock = new Object();
    private SingleGame currentSingleGame;
    private String gameType;

    public GameEngine(String gameType) {
        this.gameType = gameType;
    }

    public void generateNewGame() {
        synchronized (lock) {
            ShapeColorGame.incrementGameNumber();
            currentSingleGame = SingleGameFactory.createNewSingleGame(gameType);
        }
    }

    public void update() {
        synchronized (lock) {
            currentSingleGame.update();
        }
    }

    public void draw(Canvas canvas) {
        synchronized (lock) {
            currentSingleGame.draw(canvas);
        }
    }

    public void onScreenTouched(Point point) {
        synchronized (lock) {
            currentSingleGame.onScreenTouched(point);
        }
    }

    public int getNumberOfLookedForShapesOnScreen() {
        return currentSingleGame.getNumberOfLookedForShapesOnScreen();
    }

    public void playWonGame() {
        SoundResourcesManager.playWonGame();
    }

    public void drawGameTitleShape(Canvas canvas) {
        currentSingleGame.getGameTitleShape().draw(canvas);
    }
}
