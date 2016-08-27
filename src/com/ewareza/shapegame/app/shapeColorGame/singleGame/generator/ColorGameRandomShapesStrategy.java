package com.ewareza.shapegame.app.shapeColorGame.singleGame.generator;

import android.graphics.Rect;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;

public class ColorGameRandomShapesStrategy implements RandomShapesStrategy {
    private int maxNumberOfLookedForShapes = 2;
    private int currentNumberOfLookedForColors = 0;
    private ColorFactory.Color currentLookedForColor;
    private Rect areaToShowShapes = getAreaToShowShapes();

    @Override
    public AbstractShape generateShape() {
        ShapeFactory shapeFactory = getRandomShapeFactory();
        if (currentLookedForColor == null) {
            currentLookedForColor = ColorFactory.generateColor();
        }

        ColorFactory.Color color = ColorFactory.generateColor();
        if (currentNumberOfLookedForColors < maxNumberOfLookedForShapes)
            color = currentLookedForColor;

        return shapeFactory.getRandomShape(areaToShowShapes, color);
    }

    @Override
    public void incrementCounter(AbstractShape shape) {
        if (shape.getColor().getColorName().equals(currentLookedForColor.getColorName()))
            currentNumberOfLookedForColors++;
    }

    @Override
    public void init() {
        currentLookedForColor = null;
        currentNumberOfLookedForColors = 0;
    }
}

