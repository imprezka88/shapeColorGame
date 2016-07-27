package com.ewareza.shapegame.drawer;

import android.graphics.Canvas;
import com.ewareza.shapegame.domain.shape.AbstractShape;

public interface Drawer {
    void drawSmall(Canvas canvas, AbstractShape shape);

    void drawBig(Canvas canvas, AbstractShape abstractShape);
}
