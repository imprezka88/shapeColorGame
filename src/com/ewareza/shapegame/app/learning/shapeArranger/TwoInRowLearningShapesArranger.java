package com.ewareza.shapegame.app.learning.shapeArranger;

import android.graphics.Point;
import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ShapeFactory;

import java.util.HashMap;
import java.util.Map;

public class TwoInRowLearningShapesArranger extends LearningShapesArranger {

    @Override
    Map<ShapeFactory, Point> generateShapeToPointMap() {
        Map<ShapeFactory, Point> map = new HashMap<>();
        int padding = 50;
        int left = GameUtils.LEARNING_FROG_WIDTH + padding;
        int top = 10;

        map.put(GameSettings.getShapeFactories().get(0), new Point(left, top));

        for (int i = 1; i < GameSettings.getShapeFactories().size(); i++) {
            if (i % 2 == 0) {
                left = GameUtils.LEARNING_FROG_WIDTH + padding;
                top += ShapeFactory.getLearningShapeMaxHeight() + 10;
            } else {
                left += ShapeFactory.getLearningShapeMaxWidth() + padding;
            }

            map.put(GameSettings.getShapeFactories().get(i), new Point(left, top));
        }

        return map;
    }


}
