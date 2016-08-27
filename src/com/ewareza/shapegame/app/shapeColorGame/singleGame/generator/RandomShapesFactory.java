package com.ewareza.shapegame.app.shapeColorGame.singleGame.generator;

import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RandomShapesFactory {
    private static final Logger logger = Logger.getLogger(RandomShapesFactory.class.getName());
    private static RandomShapesFactory COLOR_GAME_SHAPES_GENERATOR = new RandomShapesFactory(new ColorGameRandomShapesStrategy());
    private static RandomShapesFactory SHAPE_GAME_SHAPES_GENERATOR = new RandomShapesFactory(new ShapeGameRandomShapesStrategy());
    private static final int MAX_NUMBER_OF_TRIES_TO_GENERATE_SHAPE = 10;
    private static final int MIN_NUMBER_OF_SHAPES = 5;
    private static final int MAX_NUMBER_OF_SHAPES = 9;

    private RandomShapesStrategy randomShapesStrategy;

    public RandomShapesFactory(RandomShapesStrategy randomShapesStrategy) {
        this.randomShapesStrategy = randomShapesStrategy;
    }

    public List<AbstractShape> generateRandomShapes() {
        randomShapesStrategy.init();
        List<AbstractShape> shapes = new ArrayList<>();
        int numberOfShapes = generateRandomNumberOfShapes();

        for (int i = 0; i < numberOfShapes; i++) {
            generateShapeIfPossible(shapes);
        }

        return shapes;
    }

    private void generateShapeIfPossible(List<AbstractShape> shapes) {
        AbstractShape shape = generateShape();
        int numberOfTriesToGenerateShape = 1;

        while (shouldGenerateDifferentShape(shape, numberOfTriesToGenerateShape, shapes)) {
            shape = generateShape();
            numberOfTriesToGenerateShape++;
        }

        if (canAddNewShape(shape, numberOfTriesToGenerateShape, shapes)) {
            shapes.add(shape);
            randomShapesStrategy.incrementCounter(shape);

        }
    }

    private boolean canAddNewShape(AbstractShape shape, int numberOfTriesToGenerateShape, List<AbstractShape> shapes) {
        return !GameUtils.collidesWithExistingShapes(shape, shapes) && numberOfTriesToGenerateShape <= MAX_NUMBER_OF_TRIES_TO_GENERATE_SHAPE;
    }

    private boolean shouldGenerateDifferentShape(AbstractShape shape, int numberOfTriesToGenerateShape, List<AbstractShape> shapes) {
        return GameUtils.collidesWithExistingShapes(shape, shapes) && numberOfTriesToGenerateShape < MAX_NUMBER_OF_TRIES_TO_GENERATE_SHAPE;
    }

    private AbstractShape generateShape() {
        return randomShapesStrategy.generateShape();
    }

    private int generateRandomNumberOfShapes() {
        return GameUtils.getRandomInt(MIN_NUMBER_OF_SHAPES, MAX_NUMBER_OF_SHAPES);
    }

    public static RandomShapesFactory newShapeGameShapesGenerator() {
        return SHAPE_GAME_SHAPES_GENERATOR;
    }

    public static RandomShapesFactory newColorGameShapesGenerator() {
        return COLOR_GAME_SHAPES_GENERATOR;
    }
}
