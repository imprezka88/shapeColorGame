package com.ewareza.shapegame.app.shapeColorGame;

import com.ewareza.shapegame.app.utils.GameUtils;

public class ShapeColorGameFactory {
    public static ShapeColorGame getGame(String gameType) {
        if (gameType.equals(GameUtils.COLOR))
            return new ColorGame();
        else if (gameType.equals(GameUtils.SHAPE))
            return new ShapeGame();

        throw new IllegalArgumentException(String.format("Not recognized game type %s", gameType));
    }
}
