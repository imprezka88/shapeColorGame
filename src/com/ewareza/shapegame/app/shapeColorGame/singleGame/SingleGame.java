package com.ewareza.shapegame.app.shapeColorGame.singleGame;

import android.graphics.Canvas;
import android.graphics.Point;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.domain.shape.Shape;
import com.ewareza.shapegame.player.SoundResourcesManager;
import com.ewareza.shapegame.resources.SoundResources;

import java.util.ArrayList;
import java.util.List;

/*
* Represents one single level of game i.e. one set one generated shapes and generated looked for shape/color
* */
public abstract class SingleGame {
    private SingleGameState singleGameState;

    private int numberOfLookedForShapesOnScreen;

    public SingleGame(SingleGameState singleGameState) {
        this.singleGameState = singleGameState;
    }

    public SingleGameState getSingleGameState() {
        return singleGameState;
    }

    public AbstractShape getCurrentLookedForObject() {
        return singleGameState.getLookedForObject();
    }

    public abstract boolean isLookedForObjectClicked(AbstractShape shape);

    public List<AbstractShape> getShapes() {
        return singleGameState.getShapes();
    }

    public abstract Shape getGameTitleShape();

    public void onScreenTouched(Point point) {
        List<AbstractShape> toRemove = new ArrayList<>();
        for (AbstractShape shape : getShapes()) {
            if (shape.contains(point) && isLookedForObjectClicked(shape)) {
                SoundResourcesManager.playCorrectShapeClickedSound();
                toRemove.add(shape);
            }
        }

        if (toRemove.isEmpty())
            SoundResourcesManager.playWrongShapeClickedSound();
        else
            removeTouchedShapes(toRemove);
    }

    private void removeTouchedShapes(List<AbstractShape> toRemove) {
        for (AbstractShape shape : toRemove) {
            getShapes().remove(shape);
            numberOfLookedForShapesOnScreen--;
        }
    }

    public int getNumberOfLookedForShapesOnScreen() {
        return numberOfLookedForShapesOnScreen;
    }

    protected void setNumberOfLookedForShapesOnScreen(int numberOfLookedForShapesOnScreen) {
        this.numberOfLookedForShapesOnScreen = numberOfLookedForShapesOnScreen;
    }

    public void update() {
        for (AbstractShape shape : getShapes()) {
            shape.move(getShapes());
        }
    }

    public void draw(Canvas canvas) {
        for (AbstractShape shape : getShapes()) {
            shape.draw(canvas);
        }
    }
}
