package com.ewareza.shapegame.app.shapeColorGame.singleGame;

import com.ewareza.shapegame.app.Game;
import com.ewareza.shapegame.domain.Splash;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.SquareFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.domain.shape.Shape;

public class SingleColorGame extends SingleGame {
    private final ColorFactory.Color currentLookedForColor;

    public SingleColorGame(SingleGameState singleGameState, ColorFactory.Color color) {
        super(singleGameState);
        currentLookedForColor = color;
        singleGameState.setNumberOfLookedForObjects(countNumberOfLookedForObjects());
        setNumberOfLookedForShapesOnScreen(singleGameState.getNumberOfLookedForObjects());
    }

    public ColorFactory.Color getCurrentLookedForColor() {
        return currentLookedForColor;
    }

    private int countNumberOfLookedForObjects() {
        int numberOfLookedForObjects = 0;
        for (AbstractShape shape : getSingleGameState().getShapes()) {
            if (isLookedForColor(shape))
                numberOfLookedForObjects++;

        }

        return numberOfLookedForObjects;
    }

    private boolean isLookedForColor(AbstractShape shape) {
        return shape.getColor() == currentLookedForColor;
    }

    @Override
    public boolean isLookedForObjectClicked(AbstractShape shape) {
        return isLookedForColor(shape);
    }

    @Override
    public Shape getGameTitleShape() {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getGameTitleShape();

        return new Splash(square.getAssociatedRect(), currentLookedForColor, Game.getDrawer());
    }
}
