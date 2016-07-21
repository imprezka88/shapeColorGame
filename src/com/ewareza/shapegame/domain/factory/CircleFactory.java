package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.Game;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;

//@TODO inner class or separate class??
public class CircleFactory extends ShapeFactory {
    private static final CircleFactory INSTANCE = new CircleFactory();

    public static CircleFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractShape getRandomShape(Rect areaToGenerateShape) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getRandomShape(areaToGenerateShape);
        return new Circle(square.getAssociatedRect(), ColorFactory.generateColor());
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
        int left = GameUtils.LEARNING_SHAPE_LEFT;
        int top = GameUtils.LEARNING_SHAPE_TOP;
        return new Circle(new Rect(left, top, left + 5, top + 10), color);
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
