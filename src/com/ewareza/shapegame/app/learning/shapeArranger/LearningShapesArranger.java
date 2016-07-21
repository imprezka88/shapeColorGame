package com.ewareza.shapegame.app.learning.shapeArranger;

import android.graphics.Point;
import com.ewareza.shapegame.domain.factory.ShapeFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class LearningShapesArranger {
    Map<ShapeFactory, Point> shapeFactoryPointMap = new HashMap<>();//generateShapeToPointMap();

    abstract Map<ShapeFactory, Point> generateShapeToPointMap();

    public Point getPosition(ShapeFactory shapeFactory) {
        return shapeFactoryPointMap.get(shapeFactory);
    }
}
