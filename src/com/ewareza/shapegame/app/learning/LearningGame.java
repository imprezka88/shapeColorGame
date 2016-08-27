package com.ewareza.shapegame.app.learning;

import android.widget.ImageView;
import com.ewareza.shapegame.player.SoundResourcesManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public enum LearningGame {
    INSTANCE;

    private final FirstPhaseLearningScreen firstPhaseScreen = FirstPhaseLearningScreen.INSTANCE;
    private AtomicBoolean shouldUpdateScreen = new AtomicBoolean(false);
    private LearningDisplayThread firstPhaseDisplayThread;
    private Map<String, ImageView> secondPhaseShapes = new HashMap<>();

    public void setToInitialState() {
        shouldUpdateScreen.set(false);
        firstPhaseScreen.setToInitialState();
    }

    public void learnNextShape() {
        FirstPhaseLearningScreen.INSTANCE.learnNextShape();
    }

    public void onPhaseOneFinished() {
        firstPhaseScreen.clearLearningShapes();
        firstPhaseDisplayThread.setFinish(true);
        showTalkingShapes();
        SoundResourcesManager.playStartLearningPhaseTwoSound();
    }

    private void showTalkingShapes() {
        for (Map.Entry<String, ImageView> nameToShape : secondPhaseShapes.entrySet()) {
            ImageView imageView = nameToShape.getValue();
            imageView.setVisibility(ImageView.VISIBLE);
            imageView.requestLayout();
        }
    }

    public boolean getShouldUpdateScreen() {
        return shouldUpdateScreen.get();
    }

    public void startPresentingShapes() {
        shouldUpdateScreen.set(true);
        SoundResourcesManager.playLearningShapePhaseOneDescriptionSound(firstPhaseScreen.getCurrentLearningShape().getName(), firstPhaseScreen.getCurrentLearningShape().getColor());
    }

    public LearningScreen getLearningScreen() {
        return firstPhaseScreen;
    }

    public void setFirstPhaseDisplayThread(LearningDisplayThread firstPhaseDisplayThread) {
        this.firstPhaseDisplayThread = firstPhaseDisplayThread;
    }

    public void setSecondPhaseShapes(Map<String, ImageView> shapes) {
        secondPhaseShapes = shapes;
    }

    public static LearningGame getInstance() {
        return INSTANCE;
    }
}
