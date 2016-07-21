package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleShapeGame;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.DimenRes;

public class SquareFactory extends ShapeFactory {
    private static final SquareFactory INSTANCE = new SquareFactory();

    public static SquareFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractShape getRandomShape(Rect areaToGenerateShape) {
        Point point = getRandomPointOnCanvas(areaToGenerateShape);
        int left = point.x;
        int top = point.y;
        int randomRectSize = getRandomRectSize();
        int right = left + randomRectSize;
        int bottom = top + randomRectSize;

        return new Square(new Rect(left, top, right, bottom), ColorFactory.generateColor());
    }

    @Override
    public Class<? extends AbstractShape> getShapeClass() {
        return Square.class;
    }

    @Override
    public String getShapeName() {
        return Square.SQUARE;
    }

    @Override
    public AbstractShape getGameTitleShape() {
        int gameTitleHeight = DimenRes.getGameTitleHeight();
        int screenWidth = DimenRes.getScreenWidth();

        int left = screenWidth / 2 - gameTitleHeight / 2;
        int right = screenWidth / 2 + gameTitleHeight / 2;
        Rect rect = new Rect(left, 0, right, gameTitleHeight);

        return new Square(GameUtils.getRectWithPadding(rect, SingleShapeGame.TITLE_SHAPE_PADDING), GameUtils.getGameTitleShapeColor());
    }

    @Override
    public AbstractShape getLearningPhaseOneShape(ColorFactory.Color color) {
        int top = GameUtils.LEARNING_SHAPE_TOP;
        int left = GameUtils.LEARNING_SHAPE_LEFT;
        int size = 5;
        return new Square(new Rect(left, top, left + size, top + size), color);
    }

    @Override
    public AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color) {
        int right = leftTop.x + ShapeFactory.getLearningShapeMaxHeight();
        int bottom = leftTop.y + ShapeFactory.getLearningShapeMaxHeight();

        return new Square(new Rect(leftTop.x, leftTop.y, right, bottom), color);
    }

    public static class Square extends RectangleFactory.Rectangle {
        private static final String SQUARE = "square";

        private Square(Rect rect, ColorFactory.Color color) {
            super(rect, color);
        }

        @Override
        public String getName() {
            return SQUARE;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return new Square(new Rect(associatedRect), getColor());
        }
    }
}
