package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.ScaledDimenRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class ShapeFactory {
    private static final List<Integer> scaledShapeSideSizes = new ArrayList<>();
    static double WIDTH_TO_HEIGHT_FACTOR = 1.5;

    static
    {
        scaledShapeSideSizes.add(ScaledDimenRes.getShapeSize1());
        scaledShapeSideSizes.add(ScaledDimenRes.getShapeSize2());
        scaledShapeSideSizes.add(ScaledDimenRes.getShapeSize3());
    }

    private final Random random = new Random();

    private static int getMaxRectHeight() {
        return Collections.max(scaledShapeSideSizes);
    }

    private static int getMaxRectWidth() {
        return (int) (Collections.max(scaledShapeSideSizes) * WIDTH_TO_HEIGHT_FACTOR);
    }

    static int getMinRectSize() {
        return Collections.min(scaledShapeSideSizes);
    }

    static int getLearningShapeMaxWidth() {
        return (int) (getMinRectSize() * WIDTH_TO_HEIGHT_FACTOR);
    }

    static int getLearningShapeMaxHeight() {
        return getMinRectSize();
    }

    public abstract AbstractShape getRandomShape(Rect areaToGenerateShape);

    public abstract Class<? extends AbstractShape> getShapeClass();

    public abstract String getShapeName();

    int getRandomRectSize() {
        return scaledShapeSideSizes.get(random.nextInt(scaledShapeSideSizes.size()));
    }

    Point getRandomPointOnCanvas(Rect areaToGenerateShape) {
        int padding = 5;
        int x = GameUtils.getRandomInt(padding + areaToGenerateShape.left, areaToGenerateShape.right - getMaxRectWidth());
        int y = GameUtils.getRandomInt(padding + areaToGenerateShape.top, areaToGenerateShape.bottom - getMaxRectHeight());

        return new Point(x, y);
    }

    public abstract AbstractShape getGameTitleShape();

    public abstract AbstractShape getLearningPhaseOneShape(ColorFactory.Color color);

    public abstract AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color);
}
