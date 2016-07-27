package com.ewareza.shapegame.app.learning;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.player.SoundResourcesManager;
import com.ewareza.shapegame.resources.ScaledDimenRes;

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
//        GameUtils.StopThread(firstPhaseDisplayThread);
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
        SoundResourcesManager.playLearningShapePhaseOneDescriptionSound(firstPhaseScreen.getCurrentLearningShape().getName());
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
