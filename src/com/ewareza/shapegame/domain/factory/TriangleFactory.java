package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.Game;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;

public class TriangleFactory extends ShapeFactory {
    private static TriangleFactory INSTANCE = new TriangleFactory();

    public static TriangleFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractShape getRandomShape(Rect areaToGenerateShape) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getRandomShape(areaToGenerateShape);
        return new Triangle(square.getAssociatedRect(), ColorFactory.generateColor());
    }

    @Override
    public Class<? extends AbstractShape> getShapeClass() {
        return Triangle.class;
    }

    @Override
    public String getShapeName() {
        return Triangle.TRIANGLE;
    }

    @Override
    public AbstractShape getGameTitleShape() {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getGameTitleShape();
        return new Triangle(square.getAssociatedRect(), GameUtils.getGameTitleShapeColor());
    }

    @Override
    public AbstractShape getLearningPhaseOneShape(ColorFactory.Color color) {
        int left = GameUtils.LEARNING_SHAPE_LEFT;
        int top = GameUtils.LEARNING_SHAPE_TOP;
        return new Triangle(new Rect(left, top, left + 5, top + 10), color);
    }

    @Override
    public AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getLearningPhaseTwoShape(leftTop, color);
        return new Triangle(square.getAssociatedRect(), color);
    }

    public static class Triangle extends AbstractShape {
        private static final String TRIANGLE = "triangle";

        private Triangle(Rect rect, ColorFactory.Color color) {
            super(rect, color, Game.getDrawer(), Game.getMover());
        }

        @Override
        public String getName() {
            return TRIANGLE;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return new Triangle(new Rect(associatedRect), getColor());
        }
    }
}