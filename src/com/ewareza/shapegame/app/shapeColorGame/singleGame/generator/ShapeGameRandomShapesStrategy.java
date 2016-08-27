package com.ewareza.shapegame.app.shapeColorGame.singleGame.generator;

import android.graphics.Rect;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;

public class ShapeGameRandomShapesStrategy implements RandomShapesStrategy {
    private ShapeFactory currentLookedForShapeFactory;
    private int currentNumberOfLookedForShapes = 0;
    private int maxNumberOfLookedForShapes = 2;
    private Rect areaToShowShapes = getAreaToShowShapes();

    @Override
    public AbstractShape generateShape() {
        ShapeFactory shapeFactory = getRandomShapeFactory();
        if (currentLookedForShapeFactory == null) {
            currentLookedForShapeFactory = shapeFactory;
        }

        if(currentNumberOfLookedForShapes < maxNumberOfLookedForShapes) {
            shapeFactory = currentLookedForShapeFactory;
        }

        return shapeFactory.getRandomShape(areaToShowShapes, ColorFactory.generateColor());
    }

    @Override
    public void incrementCounter(AbstractShape shape) {
        if(shape.getName().equals(currentLookedForShapeFactory.getShapeName())) {
            currentNumberOfLookedForShapes++;
        }
    }

    @Override
    public void init() {
        currentLookedForShapeFactory = null;
        currentNumberOfLookedForShapes = 0;
    }
}
