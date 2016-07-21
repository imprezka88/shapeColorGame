package com.ewareza.shapegame.mover;

import android.graphics.Rect;
import com.ewareza.shapegame.app.shapeColorGame.ShapeColorGame;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.DimenRes;

import java.util.List;

public class HorizontalMover implements Mover {
    @Override
    public void move(List<AbstractShape> currentShapes, AbstractShape shape) {
        //@TODO use states instead of if else?
        if (shape.getMoveRight()) {
            if (!canMoveRight(shape.getAssociatedRect())) {
                shape.setMoveRight(false);
            } else {
                moveRight(shape.getAssociatedRect());
            }
        } else {
            if (!canMoveLeft(shape.getAssociatedRect())) {
                shape.setMoveRight(true);
            } else {
                moveLeft(shape.getAssociatedRect());
            }
        }
    }

    private boolean canMoveLeft(Rect associatedRect) {
        return associatedRect.left - ShapeColorGame.getStepForCurrentGame() >= 0;
    }

    private boolean canMoveRight(Rect associatedRect) {
        return associatedRect.right + ShapeColorGame.getStepForCurrentGame() <= DimenRes.getScreenWidth();
    }

    public void moveRight(Rect associatedRect) {
        associatedRect.right += ShapeColorGame.getStepForCurrentGame();
        associatedRect.left += ShapeColorGame.getStepForCurrentGame();
    }

    public void moveLeft(Rect associatedRect) {
        associatedRect.right -= ShapeColorGame.getStepForCurrentGame();
        associatedRect.left -= ShapeColorGame.getStepForCurrentGame();
    }
}
