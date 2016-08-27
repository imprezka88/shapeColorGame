package com.ewareza.shapegame.app.shapeColorGame.singleGame.generator;

import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleColorGame;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleGame;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleGameState;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleShapeGame;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.player.SoundResourcesManager;

import java.util.List;

public class SingleGameFactory {
    private static final Object lock = new Object();

    public static SingleGame createNewSingleGame(String gameType) {

        //@TODO take game type from some list, in file or class
        if (shouldGenerateColorGame(gameType)) {
            return generateSingleColorGame();
        } else {
            return generateSingleShapeGame();
        }
    }

    private static SingleShapeGame generateSingleShapeGame() {
        RandomShapesFactory shapesGenerator = RandomShapesFactory.newShapeGameShapesGenerator();
        List<AbstractShape> shapes = shapesGenerator.generateRandomShapes();
        SingleGameState singleGameState = new SingleGameState(shapes);

        SingleShapeGame singleShapeGame = new SingleShapeGame(singleGameState, getCurrentLookedForShapeFactory(shapes.get(0)));
        SoundResourcesManager.playShapeGameTitleSound(singleShapeGame.getCurrentLookedForObjectName());

        return singleShapeGame;
    }

    private static ShapeFactory getCurrentLookedForShapeFactory(AbstractShape shape) {
        for (ShapeFactory shapeFactory : GameSettings.getShapeFactories()) {
            if(shapeFactory.getShapeClass().equals(shape.getClass()))
                return shapeFactory;
        }

        throw new IllegalArgumentException(String.format("Shape factory for shape: %s not found", shape.getName()));
    }

    private static SingleColorGame generateSingleColorGame() {
        RandomShapesFactory shapesGenerator = RandomShapesFactory.newColorGameShapesGenerator();
        List<AbstractShape> shapes = shapesGenerator.generateRandomShapes();
        SingleGameState singleGameState = new SingleGameState(shapes);

        SingleColorGame singleColorGame = new SingleColorGame(singleGameState, getCurrentLookedForColor(shapes.get(0)));
        SoundResourcesManager.playColorGameTitleSound(singleColorGame.getCurrentLookedForObject().getColor());

        return singleColorGame;
    }

    private static ColorFactory.Color getCurrentLookedForColor(AbstractShape shape) {
        return shape.getColor();
    }

    private static boolean shouldGenerateColorGame(String gameType) {
        return gameType.equals(GameUtils.COLOR);
    }
}
