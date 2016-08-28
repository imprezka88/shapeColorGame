package com.ewareza.shapegame.app.learning;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.player.SoundResourcesManager;
import com.ewareza.shapegame.resources.ImageResources;
import com.ewareza.shapegame.resources.ScaledDimenRes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum FirstPhaseLearningScreen implements LearningScreen {
    INSTANCE;

    private static final Logger Log = Logger.getLogger(FirstPhaseLearningScreen.class.getName());
    public static final int SHAPE_PADDING_BOTTOM = ScaledDimenRes.getLearningPhaseOneShapePaddingBottom();
    public static final int SHAPE_PADDING_RIGHT = ScaledDimenRes.getLearningPhaseOneShapePaddingRight();
    public static final int SHAPE_PADDING_LEFT = ScaledDimenRes.getLearningPhaseOneShapePaddingLeft();
    private final List<ShapeFactory> shapeFactories = GameSettings.getShapeFactories();
    private AtomicInteger currentLearningShapeNumber = new AtomicInteger(0);
    private List<AbstractShape> learningShapes = new ArrayList<>();

    FirstPhaseLearningScreen() {
        initLearningShapes();
    }

    public synchronized AbstractShape getCurrentLearningShape() {
        return learningShapes.size() > 0 ? learningShapes.get(currentLearningShapeNumber.get()) : null;
    }

    public void learnNextShape() {
        if (isFirstLearningPhase()) {
            if(currentLearningShapeNumber.get() < learningShapes.size() - 1) {
                currentLearningShapeNumber.incrementAndGet();
                AbstractShape currentLearningShape = getCurrentLearningShape();
                if(currentLearningShape != null)
                    SoundResourcesManager.playLearningShapePhaseOneDescriptionSound(currentLearningShape.getName(), currentLearningShape.getColor());
            }
        } else
            LearningGame.INSTANCE.onPhaseOneFinished();
    }

    public boolean isFirstLearningPhase() {
        return currentLearningShapeNumber.get() < shapeFactories.size() - 1;
    }

    private synchronized void initLearningShapes() {
        currentLearningShapeNumber.set(0);
        learningShapes = LearningShapesGenerator.generateShapesForFirstLearningPhase();
    }

    @Override
    public synchronized void drawShapes(Canvas canvas) {
        if(currentLearningShapeNumber.get() < learningShapes.size())
            learningShapes.get(currentLearningShapeNumber.get()).drawForLearning(canvas);
    }

    @Override
    public synchronized void update() {
        if(currentLearningShapeNumber.get() < learningShapes.size())
            learningShapes.get(currentLearningShapeNumber.get()).growAndFallDown();
    }

    @Override
    public void setToInitialState() {
        initLearningShapes();
    }

    @Override
    public void clearScreen(Canvas canvas) {
        Drawable drawable = ImageResources.getInstance().getLearningBackgroundImage();
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
    }

    public synchronized void clearLearningShapes() {
        learningShapes.clear();
    }
}
