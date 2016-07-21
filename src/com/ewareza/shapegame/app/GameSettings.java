package com.ewareza.shapegame.app;

import com.ewareza.shapegame.domain.factory.*;

import java.util.ArrayList;
import java.util.List;

public class GameSettings {
    private static final List<ShapeFactory> shapeFactories = new ArrayList<>();

    static {
        //@TODO retrieve generators by annotation?
        shapeFactories.add(RectangleFactory.getInstance());

        shapeFactories.add(CircleFactory.getInstance());
        shapeFactories.add(OvalFactory.getInstance());

        shapeFactories.add(SquareFactory.getInstance());
        shapeFactories.add(TriangleFactory.getInstance());

        shapeFactories.add(StarFactory.getInstance());
        shapeFactories.add(HeartFactory.getInstance());
    }

    public static List<ShapeFactory> getShapeFactories() {
        return shapeFactories;
    }
}

