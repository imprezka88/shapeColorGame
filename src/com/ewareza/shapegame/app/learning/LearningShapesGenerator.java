package com.ewareza.shapegame.app.learning;

import android.graphics.Rect;
import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.app.learning.shapeArranger.LearningShapesArranger;
import com.ewareza.shapegame.app.learning.shapeArranger.OnCloudLearningShapesArranger;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.DimenRes;

import java.util.*;

public class LearningShapesGenerator {
    private static Rect areaToGenerate = initAreaToGenerate();
    private static Map<ShapeFactory, ColorFactory.Color> shapeFactoryToColorMap = new HashMap<>();
    private static LearningShapesArranger learningShapesArranger = new OnCloudLearningShapesArranger();

    static {
        generateShapeToColorMap();
        initShapeArranger();
    }

    private static void initShapeArranger() {

    }

    public static List<AbstractShape> generateShapesForSecondLearningPhase() {
        List<AbstractShape> shapes = new ArrayList<>();
        for (ShapeFactory shapeFactory : GameSettings.getShapeFactories()) {
            shapes.add(getMaxShape(shapeFactory));
        }

        return shapes;
    }

    private static AbstractShape getMaxShape(ShapeFactory shapeFactory) {
        return shapeFactory.getLearningPhaseTwoShape(learningShapesArranger.getPosition(shapeFactory), shapeFactoryToColorMap.get(shapeFactory));
    }

    private static Rect initAreaToGenerate() {
        return new Rect(GameUtils.LEARNING_FROG_WIDTH + GameUtils.PADDING, GameUtils.PADDING, DimenRes.getScreenWidth(), DimenRes.getScreenHeight());
    }

    private static void generateShapeToColorMap() {
        List<ColorFactory.Color> shapeColors = ColorFactory.getShapeColors();
        List<ShapeFactory> shapeFactories = GameSettings.getShapeFactories();
        Collections.shuffle(shapeColors, new Random());

        for (int i = 0; i < shapeFactories.size() && i < shapeColors.size(); i++) {
            shapeFactoryToColorMap.put(shapeFactories.get(i), shapeColors.get(i));
        }
    }

    public static List<AbstractShape> generateShapesForFirstLearningPhase() {
        generateShapeToColorMap();
        List<AbstractShape> learningShapes = new ArrayList<>();
        for (ShapeFactory shapeFactory : GameSettings.getShapeFactories()) {
            learningShapes.add(shapeFactory.getLearningPhaseOneShape(shapeFactoryToColorMap.get(shapeFactory)));
        }

        return learningShapes;
    }

    public static ColorFactory.Color getLearningShapeColor(ShapeFactory shapeFactory) {
        return shapeFactoryToColorMap.get(shapeFactory);
    }
}
