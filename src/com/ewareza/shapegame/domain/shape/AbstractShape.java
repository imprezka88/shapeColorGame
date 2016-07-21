package com.ewareza.shapegame.domain.shape;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.drawer.Drawer;
import com.ewareza.shapegame.mover.Mover;
import com.ewareza.shapegame.resources.DimenRes;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public abstract class AbstractShape implements Shape, Cloneable {
    private static final Logger LOGGER = Logger.getLogger(AbstractShape.class.getName());

    private static final int GROW_STEP = 10;
    private static final int MOVE_DOWN_STEP = 8;
    private static final int MOVE_RIGHT_STEP = 3;
    protected final Rect associatedRect;
    public Drawer drawer;
    public Mover mover;
    private ColorFactory.Color color;
    private Random random = new Random();
    private boolean moveRight = random.nextBoolean();
    private int currentNumberOfExtraShapes = 0;
    private int maxNumberOfExtraShapes = 20;

    public AbstractShape(Rect associatedRect, ColorFactory.Color color, Drawer drawer) {
        this.associatedRect = associatedRect;
        this.color = color;
        this.drawer = drawer;
    }

    public AbstractShape(Rect associatedRect, ColorFactory.Color color, Drawer drawer, Mover mover) {
        this.associatedRect = associatedRect;
        this.color = color;
        this.drawer = drawer;
        this.mover = mover;
    }

    public int getCurrentNumberOfExtraShapes() {
        return currentNumberOfExtraShapes;
    }

    public void setCurrentNumberOfExtraShapes(int currentNumberOfExtraShapes) {
        this.currentNumberOfExtraShapes = currentNumberOfExtraShapes;
    }

    public int getMaxNumberOfExtraShapes() {
        return maxNumberOfExtraShapes;
    }

    @Override
    public void draw(Canvas canvas) {
        drawer.draw(canvas, this);
    }

    public ColorFactory.Color getColor() {
        return color;
    }

    public void setColor(ColorFactory.Color color) {
        this.color = color;
    }

    public boolean contains(Point point) {
        return associatedRect.contains(point.x, point.y);
    }

    public boolean intersects(AbstractShape shape) {
        return Rect.intersects(associatedRect, shape.associatedRect);
    }

    public void move(List<AbstractShape> shapes) {
        mover.move(shapes, this);
    }

    public synchronized void growAndFallDown() {
        if(canMoveAndGrow()) {
            associatedRect.left -= GROW_STEP / 2;
            associatedRect.top += MOVE_DOWN_STEP;
            associatedRect.right += GROW_STEP / 2;
            associatedRect.bottom += MOVE_DOWN_STEP + GROW_STEP;
        }
    }

    private boolean canMoveAndGrow() {
        return associatedRect.bottom < DimenRes.getScreenHeight() - 150 && associatedRect.right < DimenRes.getScreenWidth() - 20;
    }

    public abstract String getName();

    public synchronized Rect getAssociatedRect() {
        return associatedRect;
    }

    public boolean getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException;

    public synchronized void changePosition(Point point) {
        int width = associatedRect.right - associatedRect.left;
        int height = associatedRect.bottom - associatedRect.top;
        associatedRect.left = point.x;
        associatedRect.top = point.y;
        associatedRect.right = point.x + width;
        associatedRect.bottom = point.y + height;
    }

    public int getWidth() {
        return associatedRect.right - associatedRect.left;
    }

    public int getHeight() {
        return associatedRect.bottom - associatedRect.top;
    }
}
