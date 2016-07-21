package com.ewareza.shapegame.app.shapeColorGame.singleGame;

import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.domain.shape.Shape;

public class SingleShapeGame extends SingleGame {
    public static final int TITLE_SHAPE_PADDING = 5;
    private final ShapeFactory currentLookedForShapeFactory;

    public SingleShapeGame(SingleGameState singleGameState, ShapeFactory lookedForShape) {
        super(singleGameState);
        currentLookedForShapeFactory = lookedForShape;
        singleGameState.setNumberOfLookedForObjects(countNumberOfLookedForObjects());
        setNumberOfLookedForShapesOnScreen(singleGameState.getNumberOfLookedForObjects());
    }

    private int countNumberOfLookedForObjects() {
        int numberOfLookedForObjects = 0;
        for (AbstractShape shape : getSingleGameState().getShapes()) {
            if (isLookedForShape(shape))
                numberOfLookedForObjects++;
        }

        return numberOfLookedForObjects;
    }

    private boolean isLookedForShape(AbstractShape shape) {
        return shape.getClass().equals(getSingleGameState().getLookedForObject().getClass());
    }

    @Override
    public boolean isLookedForObjectClicked(AbstractShape shape) {
        return isLookedForShape(shape);
    }

    @Override
    public Shape getGameTitleShape() {
        return currentLookedForShapeFactory.getGameTitleShape();
    }
}
