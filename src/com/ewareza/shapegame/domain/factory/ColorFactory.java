package com.ewareza.shapegame.domain.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorFactory {
    private final static List<Color> shapeColors = new ArrayList<>();

    static {
        initColors();
    }

    public static List<Color> getShapeColors() {
        return shapeColors;
    }

    public static Color generateColor() {
        Random random = new Random();
        return shapeColors.get(random.nextInt(shapeColors.size()));
    }

    static void initColors() {
        for (Color color : Color.values()) {
            if (color != Color.SHAPE_TITLE)
                shapeColors.add(color);
        }
    }

    public enum Color {
        BLUE("blue"),
        RED("red"),

        GREEN("green"),
        PINK("pink"),

        YELLOW("yellow"),
        SHAPE_TITLE("white"),

        VIOLET("violet"),
        ORANGE("orange");

        private final String colorName;

        Color(String colorName) {
            this.colorName = colorName;
        }

        public String getColorName() {
            return colorName;
        }
    }
}
