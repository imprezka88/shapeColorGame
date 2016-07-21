package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.Game;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;

public class StarFactory extends ShapeFactory {
    private static final StarFactory INSTANCE = new StarFactory();

    public static StarFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractShape getRandomShape(Rect areaToGenerateShape) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getRandomShape(areaToGenerateShape);
        return new Star(square.getAssociatedRect(), ColorFactory.generateColor());
    }

    @Override
    public Class<? extends AbstractShape> getShapeClass() {
        return Star.class;
    }

    @Override
    public String getShapeName() {
        return Star.STAR;
    }

    @Override
    public AbstractShape getGameTitleShape() {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getGameTitleShape();
        return new Star(square.getAssociatedRect(), GameUtils.getGameTitleShapeColor());
    }

    @Override
    public AbstractShape getLearningPhaseOneShape(ColorFactory.Color color) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getLearningPhaseOneShape(color);
        return new Star(square.getAssociatedRect(), color);
    }

    @Override
    public AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getLearningPhaseTwoShape(leftTop, color);
        return new Star(square.getAssociatedRect(), color);
    }

    public static class Star extends AbstractShape {
        public static final String STAR = "star";

        private Star(Rect associatedRect, ColorFactory.Color color) {
            super(associatedRect, color, Game.getDrawer(), Game.getMover());
        }

        @Override
        public String getName() {
            return STAR;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return new Star(new Rect(associatedRect), getColor());
        }
    }
}
