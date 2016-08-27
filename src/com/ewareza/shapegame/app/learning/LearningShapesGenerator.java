package com.ewareza.shapegame.app.learning;

import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;

import java.util.*;

class LearningShapesGenerator {
    private static Map<ShapeFactory, ColorFactory.Color> shapeFactoryToColorMap = new HashMap<>();

    static {
        generateShapeToColorMap();
    }

    private static void generateShapeToColorMap() {
        List<ColorFactory.Color> shapeColors = ColorFactory.getShapeColors();
        List<ShapeFactory> shapeFactories = GameSettings.getShapeFactories();
        Collections.shuffle(shapeColors, new Random());

        for (int i = 0, j = 0; i < shapeFactories.size() && j < shapeColors.size(); i++) {
            shapeFactoryToColorMap.put(shapeFactories.get(i), shapeColors.get(j));
            if(j < shapeColors.size() - 1)
                j++;
        }
    }

    static List<AbstractShape> generateShapesForFirstLearningPhase() {
        generateShapeToColorMap();
        List<AbstractShape> learningShapes = new ArrayList<>();
        for (ShapeFactory shapeFactory : GameSettings.getShapeFactories()) {
            AbstractShape learningPhaseOneShape = shapeFactory.getLearningPhaseOneShape(shapeFactoryToColorMap.get(shapeFactory));
            learningPhaseOneShape.setColor(shapeFactoryToColorMap.get(shapeFactory));
            learningShapes.add(learningPhaseOneShape);
        }

        return learningShapes;
    }

    static ColorFactory.Color getLearningShapeColor(ShapeFactory shapeFactory) {
        return shapeFactoryToColorMap.get(shapeFactory);
    }
}
