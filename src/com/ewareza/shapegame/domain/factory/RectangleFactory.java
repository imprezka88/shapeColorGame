package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.Game;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleShapeGame;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.DimenRes;

public class RectangleFactory extends ShapeFactory {
    private static final RectangleFactory INSTANCE = new RectangleFactory();

    public static RectangleFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractShape getRandomShape(Rect areaToGenerateShape) {
        Point point = getRandomPointOnCanvas(areaToGenerateShape);
        int left = point.x;
        int top = point.y;
        return getShape(left, top);
    }

    @Override
    public Class<? extends AbstractShape> getShapeClass() {
        return Rectangle.class;
    }

    @Override
    public String getShapeName() {
        return Rectangle.RECTANGLE;
    }

    private AbstractShape getShape(int left, int top) {
        int randomRectSizeWidth = (int) (getRandomRectSize() * WIDTH_TO_HEIGHT_FACTOR);
        int randomRectSizeHeight = getMinRectSize();

        int right = left + randomRectSizeWidth;
        int bottom = top + randomRectSizeHeight;

        return new Rectangle(new Rect(left, top, right, bottom), ColorFactory.generateColor());
    }

    @Override
    public AbstractShape getGameTitleShape() {
        int gameTitleHeight = DimenRes.getGameTitleHeight();
        int screenWidth = DimenRes.getScreenWidth();

        int left = screenWidth / 2 - gameTitleHeight;
        int right = screenWidth / 2 + gameTitleHeight;
        Rect rect = new Rect(left, 0, right, gameTitleHeight);

        return new Rectangle(GameUtils.getRectWithPadding(rect, SingleShapeGame.TITLE_SHAPE_PADDING), GameUtils.getGameTitleShapeColor());
    }

    @Override
    public AbstractShape getLearningPhaseOneShape(ColorFactory.Color color) {
        return new Rectangle(new Rect(GameUtils.LEARNING_SHAPE_LEFT, GameUtils.LEARNING_SHAPE_TOP,
                GameUtils.LEARNING_SHAPE_LEFT + 200, GameUtils.LEARNING_SHAPE_TOP), color);
    }

    @Override
    public AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color) {
        return new Rectangle(new Rect(leftTop.x, leftTop.y, leftTop.x + ShapeFactory.getLearningShapeMaxWidth(), leftTop.y + ShapeFactory.getLearningShapeMaxHeight()), color);
    }

    public static class Rectangle extends AbstractShape {
        private static final String RECTANGLE = "rectangle";

        protected Rectangle(Rect rect, ColorFactory.Color color) {
            super(rect, color, Game.getDrawer(), Game.getMover());
        }

        @Override
        public String getName() {
            return RECTANGLE;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return new Rectangle(new Rect(associatedRect), getColor());
        }

        public Rect asRect() {
            return associatedRect;
        }
    }
}
