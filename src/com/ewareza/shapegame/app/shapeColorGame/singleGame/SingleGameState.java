package com.ewareza.shapegame.app.shapeColorGame.singleGame;

import com.ewareza.shapegame.domain.shape.AbstractShape;

import java.util.List;

public class SingleGameState {
    private final List<AbstractShape> shapes;
    private final AbstractShape lookedForObject;
    private int numberOfLookedForObjects;

    public SingleGameState(List<AbstractShape> shapes) {
        this.shapes = shapes;
        lookedForObject = getFirstGeneratedShape(shapes);
    }

    private AbstractShape getFirstGeneratedShape(List<AbstractShape> shapes) {
        return shapes.get(0);
    }

    public List<AbstractShape> getShapes() {
        return shapes;
    }

    public int getNumberOfLookedForObjects() {
        return numberOfLookedForObjects;
    }

    public void setNumberOfLookedForObjects(int numberOfLookedForObjects) {
        this.numberOfLookedForObjects = numberOfLookedForObjects;
    }

    public AbstractShape getLookedForObject() {
        return lookedForObject;
    }
}
