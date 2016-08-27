package com.ewareza.shapegame.app.shapeColorGame;

import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.resources.ImageResources;

public class ColorGame extends ShapeColorGame {
    public ColorGame() {
        super(GameUtils.COLOR);
    }

    @Override
    public String getNextGameName() {
        return GameUtils.SHAPE;
    }

    @Override
    public int getNextGameImageIdentifier() {
        return ImageResources.getShapeGameImageIdentifier();
    }
}
