package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class ShapeFactory {
    private static final List<Integer> sideSizes = new ArrayList<>();
    public static double WIDTH_TO_HEIGHT_FACTOR = 1.5;

    static
    {
        sideSizes.add(140);
        sideSizes.add(160);
        sideSizes.add(180);
    }

    private final Random random = new Random();

    public static int getMaxRectHeight() {
        return Collections.max(sideSizes);
    }

    public static int getMaxRectWidth() {
        return (int) (Collections.max(sideSizes) * WIDTH_TO_HEIGHT_FACTOR);
    }

    protected static int getMinRectSize() {
        return Collections.min(sideSizes);
    }

    public static int getLearningShapeMaxWidth() {
        return (int) (getMinRectSize() * WIDTH_TO_HEIGHT_FACTOR);
    }

    public static int getLearningShapeMaxHeight() {
        return getMinRectSize();
    }

    public abstract AbstractShape getRandomShape(Rect areaToGenerateShape);

    public abstract Class<? extends AbstractShape> getShapeClass();

    public abstract String getShapeName();

    protected int getRandomRectSize() {
        return sideSizes.get(random.nextInt(sideSizes.size()));
    }

    protected Point getRandomPointOnCanvas(Rect areaToGenerateShape) {
        int padding = 5;
        int x = GameUtils.getRandomInt(padding + areaToGenerateShape.left, (int) (areaToGenerateShape.right - getMaxRectWidth()));
        int y = GameUtils.getRandomInt(padding + areaToGenerateShape.top, areaToGenerateShape.bottom - getMaxRectHeight());

        return new Point(x, y);
    }

    public abstract AbstractShape getGameTitleShape();

    public abstract AbstractShape getLearningPhaseOneShape(ColorFactory.Color color);

    public abstract AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color);
}
