package com.ewareza.shapegame.app.shapeColorGame.singleGame.generator;

import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleColorGame;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleGame;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleGameState;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleShapeGame;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.player.SoundResourcesManager;
import com.ewareza.shapegame.resources.SoundResources;

import java.util.List;

public class SingleGameFactory {
    private static final Object lock = new Object();
    private static final RandomShapesFactory shapesGenerator = RandomShapesFactory.getInstance();

    public static SingleGame createNewSingleGame(String gameType) {
        List<AbstractShape> shapes = generateShapes();
        SingleGameState singleGameState = new SingleGameState(shapes);

        //@TODO take game type from some list, in file or class
        if (shouldGenerateColorGame(gameType)) {
            return generateSingleColorGame(singleGameState, shapes);
        } else {
            return generateSingleShapeGame(singleGameState);
        }
    }

    private static SingleShapeGame generateSingleShapeGame(SingleGameState singleGameState) {
        SingleShapeGame singleShapeGame = new SingleShapeGame(singleGameState, shapesGenerator.getCurrentLookedForShapeFactory());
        SoundResourcesManager.playShapeGameTitleSound(singleShapeGame.getCurrentLookedForObject());

        return singleShapeGame;
    }

    private static SingleColorGame generateSingleColorGame(SingleGameState singleGameState, List<AbstractShape> shapes) {
        SingleColorGame singleColorGame = new SingleColorGame(singleGameState, shapes.get(0).getColor());
        SoundResourcesManager.playColorGameTitleSound(singleColorGame.getCurrentLookedForObject().getColor());

        return singleColorGame;
    }

    private static boolean shouldGenerateColorGame(String gameType) {
        return gameType.equals(GameUtils.COLOR);
    }

    private static List<AbstractShape> generateShapes() {
        synchronized (lock) {
            return shapesGenerator.generateRandomShapes();
        }
    }
}
