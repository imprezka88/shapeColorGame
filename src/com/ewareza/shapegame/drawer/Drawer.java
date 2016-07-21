package com.ewareza.shapegame.drawer;

import android.graphics.Canvas;
import com.ewareza.shapegame.domain.shape.AbstractShape;

public interface Drawer {
    void draw(Canvas canvas, AbstractShape shape);
}
