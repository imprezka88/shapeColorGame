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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public enum FirstPhaseLearningScreen implements LearningScreen {
    INSTANCE;

    public static final int SHAPE_PADDING_BOTTOM = ScaledDimenRes.getLearningPhaseOneShapePaddingBottom();
    public static final int SHAPE_PADDING_RIGHT = ScaledDimenRes.getLearningPhaseOneShapePaddingRight();
    public static final int SHAPE_PADDING_LEFT = ScaledDimenRes.getLearningPhaseOneShapePaddingLeft();
    private final List<ShapeFactory> shapeFactories = GameSettings.getShapeFactories();
    private AtomicInteger currentLearningShapeNumber = new AtomicInteger(0);
    private List<AbstractShape> learningShapes = new ArrayList<>();

    FirstPhaseLearningScreen() {
        initLearningShapes();
    }

    AbstractShape getCurrentLearningShape() {
        ShapeFactory shapeFactory = shapeFactories.get(currentLearningShapeNumber.get());
        return shapeFactory.getGameTitleShape();
    }

    public void learnNextShape() {
        if (isFirstLearningPhase()) {
            currentLearningShapeNumber.incrementAndGet();
            SoundResourcesManager.playLearningShapePhaseOneDescriptionSound(getCurrentLearningShapeName());
        } else
            LearningGame.INSTANCE.onPhaseOneFinished();
    }

    private String getCurrentLearningShapeName() {
        return getCurrentLearningShape().getName();
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
