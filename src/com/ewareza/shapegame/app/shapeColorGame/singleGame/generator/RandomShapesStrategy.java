package com.ewareza.shapegame.app.shapeColorGame.singleGame.generator;

import android.graphics.Rect;
import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.ScaledDimenRes;

public interface RandomShapesStrategy {
    AbstractShape generateShape();

    void incrementCounter(AbstractShape shape);

    void init();

    default Rect getAreaToShowShapes() {
        return new Rect(0, ScaledDimenRes.getGameTitleHeightInPx(), ScaledDimenRes.getScreenWidthInPx(), ScaledDimenRes.getScreenHeightInPx());
    }

    default ShapeFactory getRandomShapeFactory() {
        return GameUtils.getRandomElement(GameSettings.getShapeFactories());
    }
}
