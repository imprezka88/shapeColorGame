package com.ewareza.shapegame.drawer;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.ImageResources;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidCanvasDrawer implements Drawer {
    private static final Logger LOGGER = Logger.getLogger(AndroidCanvasDrawer.class.getName());
    private Random random = new Random();

    @Override
    public void drawSmall(Canvas canvas, AbstractShape shape) {
        draw(canvas, shape, shape.getName());
    }

    private void draw(Canvas canvas, AbstractShape shape, String name) {
        String extraShapeName = String.format("%s_extra", name);

        if (shouldDrawExtraShape(shape)) {
            if (ImageResources.getInstance().hasResource(extraShapeName, shape.getColor())) {
                name = extraShapeName;
                incrementCurrentNumberOfExtra(shape);
            }
        }

        try {
            Drawable drawable = ImageResources.getInstance().getResource(name, shape.getColor());
            drawable.setBounds(shape.getAssociatedRect());
            drawable.draw(canvas);
        } catch (IllegalArgumentException e) {
            LOGGER.warning(String.format("Resource for: %s with color: %s not found", name, shape.getColor()));
        }
    }

    @Override
    public void drawBig(Canvas canvas, AbstractShape abstractShape) {
        String name = String.format("learning_%s", abstractShape.getName());
        draw(canvas, abstractShape, name);
    }

    private boolean shouldDrawExtraShape(AbstractShape shape) {
        return shouldDrawFirstExtraShape(shape) || shouldDrawNextExtraShape(shape);
    }

    private boolean shouldDrawNextExtraShape(AbstractShape shape) {
        return shape.getCurrentNumberOfExtraShapes() > 0 && shape.getCurrentNumberOfExtraShapes() < shape.getMaxNumberOfExtraShapes();
    }

    private boolean shouldDrawFirstExtraShape(AbstractShape shape) {
        return random.nextDouble() < 0.01 && shape.getCurrentNumberOfExtraShapes() == 0;
    }

    private void incrementCurrentNumberOfExtra(AbstractShape shape) {
        shape.setCurrentNumberOfExtraShapes(shape.getCurrentNumberOfExtraShapes() + 1);
        if (shape.getCurrentNumberOfExtraShapes() == shape.getMaxNumberOfExtraShapes())
            shape.setCurrentNumberOfExtraShapes(0);
    }
}
