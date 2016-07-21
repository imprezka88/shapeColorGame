package com.ewareza.shapegame.app.learning;

import android.widget.ImageView;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.player.SoundResourcesManager;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LearningGame {
    private static final FirstPhaseLearningScreen firstPhaseScreen = new FirstPhaseLearningScreen();
    private static AtomicBoolean shouldUpdateScreen = new AtomicBoolean(false);
    private static LearningDisplayThread firstPhaseDisplayThread;
    private static LearningGameActivity learningActivity;

    public static void setToInitialState() {
        shouldUpdateScreen.set(false);
        firstPhaseScreen.setToInitialState();
    }

    public static void learnNextShape() {
        FirstPhaseLearningScreen.learnNextShape();
    }

    public static void onPhaseOneFinished() {
        firstPhaseScreen.clearLearningShapes();
        firstPhaseDisplayThread.setRunning(false);
        GameUtils.StopThread(firstPhaseDisplayThread);
        learningActivity.initShapeAnimations();
        SoundResourcesManager.playStartLearningPhaseTwoSound();
    }

    public static boolean getShouldUpdateScreen() {
        return shouldUpdateScreen.get();
    }

    public static void startPresentingShapes() {
        shouldUpdateScreen.set(true);
        SoundResourcesManager.playLearningShapePhaseOneDescriptionSound(FirstPhaseLearningScreen.getCurrentLearningShape().getName());
    }

    public static LearningScreen getLearningScreen() {
        return firstPhaseScreen;
    }

    public static void setFirstPhaseDisplayThread(LearningDisplayThread firstPhaseDisplayThread) {
        LearningGame.firstPhaseDisplayThread = firstPhaseDisplayThread;
    }

    public static void setLearningActivity(LearningGameActivity learningActivity) {
        LearningGame.learningActivity = learningActivity;
    }
}
