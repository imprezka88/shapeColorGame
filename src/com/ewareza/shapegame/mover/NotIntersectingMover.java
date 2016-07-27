package com.ewareza.shapegame.mover;

import android.graphics.Rect;
import com.ewareza.shapegame.app.shapeColorGame.ShapeColorGame;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.ScaledDimenRes;

import java.util.List;

public class NotIntersectingMover implements Mover {

    @Override
    public void move(List<AbstractShape> currentShapes, AbstractShape shape) {
        //@TODO use states instead of if else?
        if (shape.getMoveRight()) {
            if (!canMoveRight(currentShapes, shape)) {
                shape.setMoveRight(false);
                moveLeft(shape.getAssociatedRect());
            } else {
                moveRight(shape.getAssociatedRect());
            }
        } else {
            if (!canMoveLeft(currentShapes, shape)) {
                shape.setMoveRight(true);
                moveRight(shape.getAssociatedRect());
            } else {
                moveLeft(shape.getAssociatedRect());
            }
        }
    }

    private boolean canMoveLeft(List<AbstractShape> shapes, AbstractShape shapeToTest) {
        AbstractShape copy = null;

        try {
            copy = (AbstractShape) shapeToTest.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        moveLeft(copy.getAssociatedRect());

        for (AbstractShape shape : shapes) {
            if (shape != shapeToTest && shape.intersects(copy)) {
                return false;
            }
        }

        return (shapeToTest.getAssociatedRect().left - ShapeColorGame.getStepForCurrentGame()) >= 0;
    }

    private boolean canMoveRight(List<AbstractShape> shapes, AbstractShape shapeToTest) {
        AbstractShape copy = null;

        try {
            copy = (AbstractShape) shapeToTest.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        moveRight(copy.getAssociatedRect());

        for (AbstractShape shape : shapes) {
            if (shape != shapeToTest && shape.intersects(copy)) {
                return false;
            }
        }

        return (shapeToTest.getAssociatedRect().right + ShapeColorGame.getStepForCurrentGame()) <= ScaledDimenRes.getScreenWidthInPx();
    }

    private void moveRight(Rect associatedRect) {
        associatedRect.right += ShapeColorGame.getStepForCurrentGame();
        associatedRect.left += ShapeColorGame.getStepForCurrentGame();
    }

    private void moveLeft(Rect associatedRect) {
        associatedRect.right -= ShapeColorGame.getStepForCurrentGame();
        associatedRect.left -= ShapeColorGame.getStepForCurrentGame();
    }
}
