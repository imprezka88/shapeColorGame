package com.ewareza.shapegame.mover;

import android.graphics.Rect;
import com.ewareza.shapegame.app.shapeColorGame.ShapeColorGame;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.DimenRes;

import java.util.List;

public class VerticalMover implements Mover {
    private boolean moveDown = true;

    @Override
    public void move(List<AbstractShape> currentShapes, AbstractShape shape) {
        if (moveDown) {
            if (!canMoveDown(shape.getAssociatedRect())) {
                moveDown = false;
            } else {
                moveDown(shape.getAssociatedRect());
            }
        } else {
            if (!canMoveUp(shape.getAssociatedRect())) {
                moveDown = true;
            } else {
                moveUp(shape.getAssociatedRect());
            }
        }
    }

    private boolean canMoveUp(Rect associatedRect) {
        return associatedRect.top - ShapeColorGame.getStepForCurrentGame() >= 0;
    }

    private boolean canMoveDown(Rect associatedRect) {
        return associatedRect.bottom + ShapeColorGame.getStepForCurrentGame() <= DimenRes.getScreenHeight();
    }

    public void moveDown(Rect associatedRect) {
        associatedRect.top += ShapeColorGame.getStepForCurrentGame();
        associatedRect.bottom += ShapeColorGame.getStepForCurrentGame();
    }

    public void moveUp(Rect associatedRect) {
        associatedRect.top -= ShapeColorGame.getStepForCurrentGame();
        associatedRect.bottom -= ShapeColorGame.getStepForCurrentGame();
    }
}
