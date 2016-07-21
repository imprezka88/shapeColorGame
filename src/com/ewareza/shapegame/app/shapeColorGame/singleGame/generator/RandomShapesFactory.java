package com.ewareza.shapegame.app.shapeColorGame.singleGame.generator;

import android.graphics.Rect;
import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.DimenRes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public enum RandomShapesFactory {
    INSTANCE;

    private static final Logger logger = Logger.getLogger(RandomShapesFactory.class.getName());
    private static final int MAX_NUMBER_OF_TRIES_TO_GENERATE_SHAPE = 10;
    private static final int MIN_NUMBER_OF_SHAPES = 5;
    private static final int MAX_NUMBER_OF_SHAPES = 7;
    private ShapeFactory currentLookedForShapeFactory;
    private Rect areaToShowShapes = getAreaToShowShapes();

    public static RandomShapesFactory getInstance() {
        return INSTANCE;
    }

    public List<AbstractShape> generateRandomShapes() {
        currentLookedForShapeFactory = null;
        List<AbstractShape> shapes = new ArrayList<>();
        int numberOfShapes = generateRandomNumberOfShapes();

        for (int i = 0; i < numberOfShapes; i++) {
            generateShapeIfPossible(shapes, i);
        }

        return shapes;
    }

    private void generateShapeIfPossible(List<AbstractShape> shapes, int shapeIndex) {
        AbstractShape shape = generateShape(shapeIndex);
        int numberOfTriesToGenerateShape = 1;

        while (shouldGenerateDifferentShape(shape, numberOfTriesToGenerateShape, shapes)) {
            shape = generateShape(shapeIndex);
            numberOfTriesToGenerateShape++;
        }

        if (canAddNewShape(shape, numberOfTriesToGenerateShape, shapes)) {
            shapes.add(shape);
        }
    }

    private boolean canAddNewShape(AbstractShape shape, int numberOfTriesToGenerateShape, List<AbstractShape> shapes) {
        return !GameUtils.collidesWithExistingShapes(shape, shapes) && numberOfTriesToGenerateShape <= MAX_NUMBER_OF_TRIES_TO_GENERATE_SHAPE;
    }

    private boolean shouldGenerateDifferentShape(AbstractShape shape, int numberOfTriesToGenerateShape, List<AbstractShape> shapes) {
        return GameUtils.collidesWithExistingShapes(shape, shapes) && numberOfTriesToGenerateShape < MAX_NUMBER_OF_TRIES_TO_GENERATE_SHAPE;
    }

    public ShapeFactory getCurrentLookedForShapeFactory() {
        return currentLookedForShapeFactory;
    }

    private AbstractShape generateShape(int shapeIndex) {
        //@TODO get classes implementing Shape through reflection
        ShapeFactory shapeFactory = getRandomShapeFactory();
        if (currentLookedForShapeFactory == null)
            currentLookedForShapeFactory = shapeFactory;


        return shapeFactory.getRandomShape(areaToShowShapes);
    }

    private ShapeFactory getRandomShapeFactory() {
        return GameUtils.getRandomElement(GameSettings.getShapeFactories());
    }

    private int generateRandomNumberOfShapes() {
        return GameUtils.getRandomInt(MIN_NUMBER_OF_SHAPES, MAX_NUMBER_OF_SHAPES);
    }

    private Rect getAreaToShowShapes() {
        return new Rect(0, DimenRes.getGameTitleHeight(), DimenRes.getScreenWidth(), DimenRes.getScreenHeight());
    }
}
