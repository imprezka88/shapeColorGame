package com.ewareza.shapegame.app.learning.shapeArranger;

import android.graphics.Point;
import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.resources.DimenRes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnCloudLearningShapesArranger extends LearningShapesArranger {
    @Override
    Map<ShapeFactory, Point> generateShapeToPointMap() {
        Map<ShapeFactory, Point> map = new HashMap<>();
        int paddingBetweenShapes = 15;

        List<ShapeFactory> shapeFactories = GameSettings.getShapeFactories();

        map.put(shapeFactories.get(0), new Point(40, DimenRes.getScreenHeight() / 2 - 70));

        map.put(shapeFactories.get(1), new Point(100, 100));
        map.put(shapeFactories.get(2), new Point(100 + ShapeFactory.getLearningShapeMaxHeight() + paddingBetweenShapes, 100));

        map.put(shapeFactories.get(3), new Point(DimenRes.getScreenWidth() / 2, 100));
        map.put(shapeFactories.get(4), new Point(DimenRes.getScreenWidth() / 2 + paddingBetweenShapes + ShapeFactory.getLearningShapeMaxHeight() + paddingBetweenShapes, 100));

        map.put(shapeFactories.get(5), new Point(DimenRes.getScreenWidth() - 350, DimenRes.getScreenHeight() / 2 - 100));
        map.put(shapeFactories.get(6), new Point(DimenRes.getScreenWidth() - 350 + paddingBetweenShapes + ShapeFactory.getLearningShapeMaxHeight(), DimenRes.getScreenHeight() / 2 - 100));

        return map;
    }
}
