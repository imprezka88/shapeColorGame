package com.ewareza.shapegame.app.learning;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LearningView extends SurfaceView implements SurfaceHolder.Callback {
    private LearningDisplayThread learningDisplayThread;
    private SurfaceHolder holder;

    public LearningView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);

        learningDisplayThread = new LearningDisplayThread(holder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!learningDisplayThread.isRunning()) {
            //@TODO check if needed
            learningDisplayThread = new LearningDisplayThread(holder);
            LearningGame.setFirstPhaseDisplayThread(learningDisplayThread);
            learningDisplayThread.setRunning(true);
            //@TODO change to learning sounds, change not to generate titleShape - take class in different way
            learningDisplayThread.start();
        } else {
            learningDisplayThread.setRunning(true);
            learningDisplayThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        learningDisplayThread.setRunning(false);
//        GameUtils.StopThread(learningDisplayThread);
    }
}
