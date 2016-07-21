package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.Game;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;

public class HeartFactory extends ShapeFactory {
    private static final HeartFactory INSTANCE = new HeartFactory();

    public static HeartFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractShape getRandomShape(Rect areaToGenerateShape) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getRandomShape(areaToGenerateShape);
        return new Heart(square.asRect(), ColorFactory.generateColor());
    }

    @Override
    public Class<? extends AbstractShape> getShapeClass() {
        return Heart.class;
    }

    @Override
    public String getShapeName() {
        return Heart.HEART;
    }

    @Override
    public AbstractShape getGameTitleShape() {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getGameTitleShape();
        return new Heart(square.asRect(), GameUtils.getGameTitleShapeColor());
    }

    @Override
    public AbstractShape getLearningPhaseOneShape(ColorFactory.Color color) {
        int left = GameUtils.LEARNING_SHAPE_LEFT;
        int top = GameUtils.LEARNING_SHAPE_TOP;
        return new Heart(new Rect(left, top, left + 5, top + 10), color);
    }

    @Override
    public AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color) {
        SquareFactory.Square square = (SquareFactory.Square) SquareFactory.getInstance().getLearningPhaseTwoShape(leftTop, color);
        return new Heart(square.asRect(), color);
    }

    private static class Heart extends AbstractShape {
        private static final String HEART = "heart";

        private Heart(Rect rect, ColorFactory.Color color) {
            super(rect, color, Game.getDrawer(), Game.getMover());
        }

        @Override
        public String getName() {
            return HEART;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return new Heart(new Rect(associatedRect), getColor());
        }
    }
}
