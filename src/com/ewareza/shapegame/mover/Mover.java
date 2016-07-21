package com.ewareza.shapegame.mover;

import com.ewareza.shapegame.domain.shape.AbstractShape;

import java.util.List;

public interface Mover {
    void move(List<AbstractShape> currentShapes, AbstractShape abstractShape);
}
