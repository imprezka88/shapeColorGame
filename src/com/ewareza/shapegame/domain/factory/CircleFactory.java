package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.shapeColorGame.Game;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;

public class CircleFactory extends ShapeFactory {
    private static final CircleFactory INSTANCE = new CircleFactory();

    public static CircleFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractShape getRandomShape(Rect areaToGenerateShape, ColorFactory.Color color) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getRandomShape(areaToGenerateShape, color);
        return new Circle(square.getAssociatedRect(), color);
    }

    @Override
    public Class<? extends AbstractShape> getShapeClass() {
        return Circle.class;
    }

    @Override
    public String getShapeName() {
        return Circle.CIRCLE;
    }

    @Override
    public AbstractShape getGameTitleShape() {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getGameTitleShape();
        return new Circle(square.getAssociatedRect(), GameUtils.getGameTitleShapeColor());
    }

    @Override
    public AbstractShape getLearningPhaseOneShape(ColorFactory.Color color) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getLearningPhaseOneShape(color);
        return new Circle(square.asRect(), color);
    }

    @Override
    public AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getLearningPhaseTwoShape(leftTop, color);
        return new Circle(square.getAssociatedRect(), color);
    }

    public static class Circle extends AbstractShape {
        private final static String CIRCLE = "circle";

        private Circle(Rect rect, ColorFactory.Color color) {
            super(rect, color, Game.getDrawer(), Game.getMover());
        }

        @Override
        public String getName() {
            return CIRCLE;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return new Circle(getAssociatedRect(), getColor());
        }
    }
}
