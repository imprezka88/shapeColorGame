package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.Game;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;

public class OvalFactory extends ShapeFactory {
    private static final OvalFactory INSTANCE = new OvalFactory();

    public static ShapeFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractShape getRandomShape(Rect areaToGenerateShape) {
        RectangleFactory.Rectangle rectangle = (RectangleFactory.Rectangle) RectangleFactory.getInstance().getRandomShape(areaToGenerateShape);
        return new Oval(rectangle.asRect(), ColorFactory.generateColor());
    }

    @Override
    public Class<? extends AbstractShape> getShapeClass() {
        return Oval.class;
    }

    @Override
    public String getShapeName() {
        return Oval.OVAL;
    }

    @Override
    public AbstractShape getGameTitleShape() {
        RectangleFactory.Rectangle rectangle = (RectangleFactory.Rectangle) RectangleFactory.getInstance().getGameTitleShape();
        Rect rect = rectangle.asRect();

        return new Oval(rect, GameUtils.getGameTitleShapeColor());
    }

    @Override
    public AbstractShape getLearningPhaseOneShape(ColorFactory.Color color) {
        Rect rect = RectangleFactory.getInstance().getLearningPhaseOneShape(color).getAssociatedRect();
        return new Oval(rect, color);
    }

    @Override
    public AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color) {
        RectangleFactory.Rectangle rectangle = (RectangleFactory.Rectangle) RectangleFactory.getInstance().getLearningPhaseTwoShape(leftTop, color);
        return new Oval(rectangle.asRect(), color);
    }

    public static class Oval extends AbstractShape {
        private static final String OVAL = "oval";

        private Oval(Rect rect, ColorFactory.Color color) {
            super(rect, color, Game.getDrawer(), Game.getMover());
        }

        @Override
        public String getName() {
            return OVAL;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return new Oval(new Rect(associatedRect), getColor());
        }
    }
}
