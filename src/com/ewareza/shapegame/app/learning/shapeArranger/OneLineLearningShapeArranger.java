package com.ewareza.shapegame.app.learning.shapeArranger;

import android.graphics.Point;
import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.resources.DimenRes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneLineLearningShapeArranger extends LearningShapesArranger {
    @Override
    Map<ShapeFactory, Point> generateShapeToPointMap() {
        Map<ShapeFactory, Point> map = new HashMap<>();

        int left = GameUtils.PADDING;
        int top = DimenRes.getScreenHeight() - ShapeFactory.getLearningShapeMaxHeight();

        List<ShapeFactory> shapeFactories = GameSettings.getShapeFactories();
        map.put(shapeFactories.get(0), new Point(left, top));

        for (int i = 1; i < shapeFactories.size(); i++) {
            left += shapeFactories.get(i - 1).getLearningPhaseTwoShape(new Point(), ColorFactory.Color.BLUE).getWidth() + GameUtils.PADDING;
            map.put(shapeFactories.get(i), new Point(left, top));
        }

        return map;
    }
}
