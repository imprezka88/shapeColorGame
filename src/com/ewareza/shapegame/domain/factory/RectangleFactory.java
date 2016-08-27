package com.ewareza.shapegame.domain.factory;

import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.app.shapeColorGame.Game;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleShapeGame;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.ScaledDimenRes;

public class RectangleFactory extends ShapeFactory {
    private static final RectangleFactory INSTANCE = new RectangleFactory();

    public static RectangleFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractShape getRandomShape(Rect areaToGenerateShape, ColorFactory.Color color) {
        Point point = getRandomPointOnCanvas(areaToGenerateShape);
        int left = point.x;
        int top = point.y;
        return getShape(left, top, color);
    }

    @Override
    public Class<? extends AbstractShape> getShapeClass() {
        return Rectangle.class;
    }

    @Override
    public String getShapeName() {
        return Rectangle.RECTANGLE;
    }

    private AbstractShape getShape(int left, int top, ColorFactory.Color color) {
        int randomRectSizeWidth = (int) (getRandomRectSize() * WIDTH_TO_HEIGHT_FACTOR);
        int randomRectSizeHeight = getMinRectSize();

        int right = left + randomRectSizeWidth;
        int bottom = top + randomRectSizeHeight;

        return new Rectangle(new Rect(left, top, right, bottom), color);
    }

    @Override
    public AbstractShape getGameTitleShape() {
        int gameTitleHeight = ScaledDimenRes.getGameTitleHeightInPx();
        int screenWidth = ScaledDimenRes.getScreenWidthInPx();

        int left = screenWidth / 2 - gameTitleHeight;
        int right = screenWidth / 2 + gameTitleHeight;
        Rect rect = new Rect(left, 0, right, gameTitleHeight);

        return new Rectangle(GameUtils.getRectWithPadding(rect, SingleShapeGame.TITLE_SHAPE_PADDING), GameUtils.getGameTitleShapeColor());
    }

    @Override
    public AbstractShape getLearningPhaseOneShape(ColorFactory.Color color) {
        int left = GameUtils.LEARNING_SHAPE_LEFT - 40;
        int top = GameUtils.LEARNING_SHAPE_TOP;
        int right = left + 80;
        int bottom = GameUtils.LEARNING_SHAPE_TOP + 1;

        return new Rectangle(new Rect(left, top, right, bottom), color);
    }

    @Override
    public AbstractShape getLearningPhaseTwoShape(Point leftTop, ColorFactory.Color color) {
        return new Rectangle(new Rect(leftTop.x, leftTop.y, leftTop.x + ShapeFactory.getLearningShapeMaxWidth(), leftTop.y + ShapeFactory.getLearningShapeMaxHeight()), color);
    }

    static class Rectangle extends AbstractShape {
        private static final String RECTANGLE = "rectangle";

        Rectangle(Rect rect, ColorFactory.Color color) {
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

        Rect asRect() {
            return associatedRect;
        }
    }
}
